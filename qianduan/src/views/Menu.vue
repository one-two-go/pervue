<template>
	<div>
		<el-container class="home-container">
			<el-header class="home-header">
				<i class="home_title" style="margin-right: 15px">快速开发平台</i>
				<el-dropdown @command="handleCommand">
					<span style="margin-right: 50px">{{this.$store.state.user.username}}</span>
					<el-dropdown-menu slot="dropdown">
						<el-dropdown-item command="a">个人信息</el-dropdown-item>
						<el-dropdown-item command="b">修改密码</el-dropdown-item>
						<el-dropdown-item command="c">退出</el-dropdown-item>
					</el-dropdown-menu>
				</el-dropdown>
			</el-header>
			<el-container>
				<el-aside width="150px" class="home-aside">
					<el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
						<el-radio-button :label="false">展开</el-radio-button>
						<el-radio-button :label="true">收起</el-radio-button>
					</el-radio-group>
					<!-- 是否使用 vue-router 的模式，启用该模式会在激活导航时以 index 作为 path 进行路由跳转 -->
					<el-menu router :collapse="isCollapse">
						<!--一级菜单-->
						<el-submenu :index="index+''" v-for="(item,index) in this.$router.options.routes[1].children">
							<!--标题-->
							<template slot="title">
								<i class="el-icon-mouse"></i>
								<span>{{item.name}}</span>
							</template>

							<!--菜单项所在的组-->
							<el-menu-item-group>
								<!--菜单项（二级菜单）-->
								<el-menu-item :index="sub.path" v-for="(sub) in item.children">{{sub.name}}</el-menu-item>
							</el-menu-item-group>
						</el-submenu>
					</el-menu>
				</el-aside>
				<!--Test.vue加入 -->
				<el-main>
					<router-view /> <!-- 路由出口-->
				</el-main>
			</el-container>
		</el-container>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				isCollapse: true,
				menus: []
			}
		},
		created() {
			// this.initData();
		},
		methods: {
			initData() {
				this.axios.get('http://localhost:8083/menu/menuByname', {
					params: {
						username: 'lisi'
					}
				}).then((res) => {
					this.menus = res.data.result;
					console.log(this.menus)
				})
			},
			handleCommand(command) {
				if (command == 'a') {
					console.log("个人信息")
				} else if (command == 'b') {
					console.log("修改密码")
				} else {
					this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
						this.axios.get('http://localhost:8083/user/outLogin').then((res)=>{
							// 清空state数据
							this.$store.state.user={username:''};
							// 回到登录页面
							this.$router.replace({path:'/'});
						})
					}).catch(() => {
						this.$message({
							type: 'info',
							message: '取消操作'
						});
					});
				}
			}
		}
	}
</script>

<style>
	.el-menu-vertical-demo:not(.el-menu--collapse) {
		width: 200px;
		min-height: 400px;
	}

	.home-container {
		height: 100%;
		position: absolute;
		top: 0px;
		left: 0px;
		width: 100%;
	}

	.home-header {
		background-color: #20a0ff;
		color: #333;
		text-align: center;
		display: flex;
		align-items: center;
		justify-content: space-between;
		box-sizing: content-box;
		padding: 0px;
	}

	.home_title {
		color: #fff;
		font-size: 22px;
		display: inline;
		margin-left: 8px;
	}

	.home-aside {
		background-color: #ececec;
	}
</style>
