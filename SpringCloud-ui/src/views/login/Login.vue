<script setup lang="ts">
import {ref} from 'vue'

defineProps<{
    msg: string
}>()

const username = ref('')
const password = ref('')
const value = ref('')
const options = ref(['admin', 'user'])
const handleSubmit = () => {
    alert('-- form submit --')
}
</script>

<template></template>
<style scoped></style>
