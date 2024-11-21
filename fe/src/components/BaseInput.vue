<template>
  <div class="flex items-center justify-center">
    <div class="items-center relative w-full">
      <input
        class="border-b border-gray-300 py-1 focus:border-b-2 focus:border-blue-400 transition-colors focus:outline-none peer bg-inherit text-3xl w-full"
        :id="name"
        :name="name"
        :type="type"
        placeholder=""
        :value="modelValue"
        @input="$emit('update:modelValue', $event.target.value)"
      />
      <label
        :for="name"
        class="absolute -top-4 text-xs left-0 cursor-text peer-focus:text-base peer-focus:-top-4 transition-all peer-focus:text-blue-400 peer-placeholder-shown:top-1 peer-placeholder-shown:text-xl"
      >
        {{ label }}
      </label>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'

const type = ref('text')
const { name, label, password, modelValue, number } = defineProps({
  name: {
    type: String,
    required: true,
  },
  label: {
    type: String,
    required: true,
  },
  password: {
    type: Boolean,
    default: false,
  },
  modelValue: {
    type: [String, Number],
    required: false,
    default: '',
  },
  number: {
    type: Boolean,
    default: false,
  },
})

onMounted(() => {
  if (password) {
    type.value = 'password'
  } else if (number) {
    type.value = 'number'
  }
})
</script>
