<template>
  <a-layout id="components-layout-demo-top-side-2" class="flex-container">
    <the-header></the-header>
      <router-view/>
    <the-footer></the-footer>
  </a-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TheHeader from '@/components/the-header.vue';
import TheFooter from '@/components/the-footer.vue';

export default defineComponent({
  name: 'app',
  components: {
    TheHeader,
    TheFooter,
  },
});
</script>

<style scoped>
.flex-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

</style>