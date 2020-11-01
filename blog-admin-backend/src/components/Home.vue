<template>
  <div>
    <el-container class="home-container">
      <el-header class="home-header">
        <span>机器人</span>
        <div class="home-header-info">
          <i class="el-icon-chat-dot-round" style="margin-right: 20px; font-size:24px;"></i>
          <el-dropdown @command="handleCommand">
            <div style="display: flex;align-items: center; cursor: pointer;">
              <span>{{username}}</span>
              <i>
                <img
                  style="width: 40px;height: 40px;border-radius: 40px"
                  src="http://bpic.588ku.com/element_pic/01/40/00/64573ce2edc0728.jpg"
                >
              </i>
            </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="user">个人中心</el-dropdown-item>
              <el-dropdown-item command="setting">设置</el-dropdown-item>
              <el-dropdown-item command="logout" divided>注销</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
        <el-aside class="home-aside" style="display: flex;justify-content: flex-start;width:180px;text-align: left;">
          <el-menu unique-opened router style="width: 180px;">
            <template v-for="(item,index) in this.routes">
                <el-submenu :key="index+''" :index="index+''" v-if="item.children && item.children.length != 0" >
                    <template slot="title" >
                        <span>{{item.meta.title}}</span>
                    </template>
                    <el-menu-item style="padding-left: 35px" width="180px" v-for="child in item.children"  :index="child.path"  :key="child.path">
                        <span>{{child.meta.title}}</span>
                    </el-menu-item>                    
                </el-submenu>
            </template>
          </el-menu>
        </el-aside>
        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-text="this.$router.currentRoute.meta.title"></el-breadcrumb-item>
          </el-breadcrumb>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: "系统管理员"
    };
  },
  methods: {
    handleCommand(command) {
      console.log("点击下拉：" + command);
      //    this.$message("点击下拉："+ command)
    }
  },
  computed: {
    routes() {
      console.log("router：", this.$router.options.routes);
      return this.$router.options.routes;
    }
  }
};
</script>


<style>
.home-container {
  height: 100%;
  width: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
}

.home-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100px;
  background: #20a0ff;
  width: 100%;
}

.home-header > span {
  font-size: 16px;
  color: white;
  font-family: Georgia, "Times New Roman", Times, serif;
}

.home-header-info {
  display: flex;
  align-items: center;
}

</style>
