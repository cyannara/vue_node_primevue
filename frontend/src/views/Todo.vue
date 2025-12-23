<template>
    <div class="grid">
        <div class="col-12 md:col-6 md:col-offset-3">
            <Card>
                <template #title> 📝 Todo List </template>

                <template #content>
                    <!-- 입력 -->
                    <div class="flex gap-2 mb-3">
                        <InputText v-model="newTodo" placeholder="할 일을 입력하세요" class="w-full" @keyup.enter="addTodo" />
                        <Button label="추가" icon="pi pi-plus" @click="addTodo" />
                    </div>

                    <!-- 필터 -->
                    <div class="flex gap-2 mb-3">
                        <Button label="전체" size="small" :outlined="filter !== 'all'" @click="filter = 'all'" />
                        <Button label="미완료" size="small" :outlined="filter !== 'active'" @click="filter = 'active'" />
                        <Button label="완료" size="small" :outlined="filter !== 'done'" @click="filter = 'done'" />
                    </div>

                    <Divider />

                    <!-- 목록 -->
                    <ul class="list-none p-0 m-0">
                        <li v-for="todo in filteredTodos" :key="todo.id" class="flex align-items-center justify-content-between py-2">
                            <div class="flex align-items-center gap-2 w-full">
                                <Checkbox v-model="todo.done" binary />

                                <!-- 보기 -->
                                <span v-if="editId !== todo.id" :class="{ 'line-through text-color-secondary': todo.done }" class="flex-grow-1">
                                    {{ todo.text }}
                                </span>

                                <!-- 수정 -->
                                <InputText v-else v-model="editText" class="w-full" @keyup.enter="saveEdit(todo)" @blur="saveEdit(todo)" />
                            </div>

                            <div class="flex gap-1">
                                <Button v-if="editId !== todo.id" icon="pi pi-pencil" text @click="startEdit(todo)" />
                                <Button icon="pi pi-trash" severity="danger" text @click="removeTodo(todo.id)" />
                            </div>
                        </li>
                    </ul>

                    <p v-if="filteredTodos.length === 0" class="text-center text-color-secondary mt-3">표시할 Todo가 없습니다 🙂</p>
                </template>
            </Card>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue';

const STORAGE_KEY = 'sakai-todos';

const newTodo = ref('');
const todos = ref([]);
const filter = ref('all');

const editId = ref(null);
const editText = ref('');

/* localStorage 불러오기 */
onMounted(() => {
    const saved = localStorage.getItem(STORAGE_KEY);
    if (saved) {
        todos.value = JSON.parse(saved);
    }
});

/* localStorage 저장 */
watch(
    todos,
    (val) => {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(val));
    },
    { deep: true }
);

/* Todo 추가 */
const addTodo = () => {
    if (!newTodo.value.trim()) return;

    todos.value.push({
        id: Date.now(),
        text: newTodo.value,
        done: false
    });
    newTodo.value = '';
};

/* 삭제 */
const removeTodo = (id) => {
    todos.value = todos.value.filter((t) => t.id !== id);
};

/* 수정 시작 */
const startEdit = (todo) => {
    editId.value = todo.id;
    editText.value = todo.text;
};

/* 수정 저장 */
const saveEdit = (todo) => {
    if (!editText.value.trim()) return;
    todo.text = editText.value;
    editId.value = null;
};

/* 필터링 */
const filteredTodos = computed(() => {
    if (filter.value === 'active') {
        return todos.value.filter((t) => !t.done);
    }
    if (filter.value === 'done') {
        return todos.value.filter((t) => t.done);
    }
    return todos.value;
});
</script>

<style scoped>
.line-through {
    text-decoration: line-through;
}
</style>
