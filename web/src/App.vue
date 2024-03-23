<template>
  <a-layout id="components-layout-demo-top-side-2">
    <the-header></the-header>
    <router-view/>
    <a-layout-footer style="text-align: center;height: 100px;width: 100%;position: absolute;bottom: 0;left: 0">
      Zoe's Blog
    </a-layout-footer>
  </a-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TheHeader from '@/components/the-header.vue'; // @ is an alias to /src


export default defineComponent({
  name: 'app',
  props: {
    msg: String,
  },
  components: {
    TheHeader,
  },
});
</script>


<style>
#components-layout-demo-top-side-2 .logo {
  width: 120px;
  height: 31px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px 28px 16px 0;
  float: left;
}
</style>
<script setup lang="ts">
</script>