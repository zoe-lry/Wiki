<template>
  <a-layout-footer style="text-align: center;height: 100px;width: 100%;position: absolute;bottom: 0;left: 0">
    Zoe's Blog
  </a-layout-footer>
</template>


<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'the-footer',
  props: {
    msg: String,
  },
});
</script>