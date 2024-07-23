<template>
  <a-layout-header class="header">
    <div class="logo"/>
    <!--    退出登录    -->

    <a-popconfirm
        title="Are you sure to log out?"
        ok-text="Yes"
        cancel-text="No"
        @confirm="logout()"
    >
      <a class="login-menu"  v-show="user.id">
        <span>Log Out</span>
      </a>
    </a-popconfirm>
    <!--    登录    -->
    <a class="login-menu" v-show="user.id" >
      <span>Hello, {{user.name}}</span>
    </a>
    <a class="login-menu" @click="showLoginModal" v-show="!user.id">
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
      <a-menu-item key="/admin/user" :style="user.id? {}:{display:'none'}">
        <router-link to="/admin/User">Users</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/ebook" :style="user.id? {}:{display:'none'}">
        <router-link to="/admin/ebook">Ebooks</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/Category" :style="user.id? {}:{display:'none'}">
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
import {computed, defineComponent, ref} from 'vue';
import axios from 'axios';
import { message } from 'ant-design-vue';
import store from "@/store";

declare let hexMd5: any;
declare let KEY: any;

export default defineComponent({
  name: 'the-header',
  setup () {
    // 登录后保存
    const user = computed(() => store.state.user);

    //用来登录
    const loginUser = ref({
      loginName: "test1",
      password: "test123"
    });
    const loginModalVisible = ref(false);
    const loginModalLoading = ref(false);
    const showLoginModal = () => {
      loginModalVisible.value = true;
    };

    // 登录
    const login = () => {
      console.log("开始登录");
      loginModalLoading.value = true;
      loginUser.value.password = hexMd5(loginUser.value.password + KEY);
      axios.post('/user/login', loginUser.value).then((response) => {
        loginModalLoading.value = false;
        const data = response.data;
        if (data.success) {
          loginModalVisible.value = false;
          message.success("登录成功！");
          store.commit("setUser", data.content);
        } else {
          message.error(data.message);
        }
      });
    }

    // 退出登录
    const logout = () => {
      console.log("开始退出登录");
      axios.get('/user/logout/'+ user.value.token).then((response) => {
        const data = response.data;
        if (data.success) {
          loginModalVisible.value = false;
          message.success("退出登录成功！");
          store.commit("setUser", {});
        } else {
          message.error(data.message);
        }
      });
    };

    return {
      loginModalVisible,
      loginModalLoading,
      showLoginModal,
      loginUser,
      login,
      logout,
      user,

    }
  }
});
</script>


<style>
.login-menu {
  float: right;
  color: white;
  padding: 10px;
}
</style>
