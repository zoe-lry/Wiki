<template>
  <a-layout-header class="header">
    <div class="logo"/>
    <!--    登录    -->
    <a class="login-menu" @click="showLoginModal">
      <span>Log In</span>
    </a>

    <a-menu
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >

      <a-menu-item key="/home">
        <router-link to="/">Home Page</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/user">
        <router-link to="/admin/User">Users</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/ebook">
        <router-link to="/admin/ebook">Ebooks</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/Category">
        <router-link to="/admin/Category">Category</router-link>
      </a-menu-item>
      <a-menu-item key="/about">
        <router-link to="/about">About us</router-link>
      </a-menu-item>

    </a-menu>

    <a-modal
        title="登录"
        v-model:visible="loginModalVisible"
        :confirm-loading="loginModalLoading"
        @ok="login"
    >
      <a-form :model="loginUser" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
        <a-form-item label="登录名">
          <a-input v-model:value="loginUser.loginName" />
        </a-form-item>
        <a-form-item label="密码">
          <a-input v-model:value="loginUser.password" type="password" />
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout-header>
</template>


<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'the-header',
  setup () {
    const loginUser = ref({
      loginName: "test",
      password: "test"
    });
    const loginModalVisible = ref(false);
    const loginModalLoading = ref(false);
    const showLoginModal = () => {
      loginModalVisible.value = true;
    };

    // 登录
    const login = () => {
      console.log("开始登录")
    };

    return {
      loginModalVisible,
      loginModalLoading,
      showLoginModal,
      loginUser,
      login
    }
  }
});
</script>


<style>
.login-menu {
  float: right;
  color: white;
}
</style>
