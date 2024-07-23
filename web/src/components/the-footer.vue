<template>
  <a-layout-footer style="text-align: center;height: 100px;width: 100%;position: absolute;bottom: 0;left: 0">
    Zoe's. Welcome {{user.name}}
  </a-layout-footer>
</template>


<script lang="ts">
  import { defineComponent , computed} from 'vue';
  import store from "@/store";

  export default defineComponent({
    name: 'the-footer',
    setup() {
      const user = computed(() => store.state.user);

      return {
        user
      }
    }
  })

</script>