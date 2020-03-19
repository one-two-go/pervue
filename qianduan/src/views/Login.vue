<template>
	<div>
		<el-form ref="loginForm" :model="loginForm" :rules="loginFormRules" label-width="100px">
			<div class="login-box">
				<div style="margin-top:10%;margin-left:30%;width:100%;height:100%">
					<el-row>
						<el-col :span="8">
							<!-- <el-input  v-model="loginForm.username" placeholder="请输入账号" clearable>
								<template slot="prepend">账号</template>
							</el-input> -->	
							<!--el-from-item 用户名-->
							 <el-form-item>
								 <el-input  v-model="loginForm.username" placeholder="请输入账号" clearable style="width: 200px;">
									 <template slot="prepend">账号</template>
								 </el-input>
							</el-form-item> 
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="8">
							<!-- <el-input i v-model="loginForm.password" placeholder="请输入密码" type="password" clearable
							 show-password> 
								<template slot="prepend">密码</template>
							</el-input> -->
							<el-form-item>
								 <el-input  v-model="loginForm.password" placeholder="请输入密码" clearable style="width:200px;">
									 <template slot="prepend">账号</template>
								 </el-input>
							</el-form-item> 
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="8">
							<el-button style="width:20%;margin-left:35%" type="primary" @click="submitForm('loginForm')">登录</el-button>
							<el-button style="width:20%;margin-left:20%" type="primary" @click="resetForm('loginForm')">重置</el-button>
						</el-col>
					</el-row>
				</div>
			</div>
		</el-form>
	</div>
</template>

<script>
	export default {
		name: 'login',
		data() {
			return {
				loginForm: {
					username: "",
					password: ""
				},
				loginFormRules: {
					username: [{
						required: true,
						message: '用户名不能为空',
						trigger: 'blur'
					}],
					 password: [
					   { required: true, message: '请输入登录密码', trigger: 'blur' },
					   { min: 6, max: 12, message: '长度在6到12 个字符', trigger: 'blur' }
					  ]
				}
			}
		},
		methods:{
		submitForm(formName) {
				console.log("@@@@@"+this.loginForm)	
			    //表单验证
		        this.$refs[formName].validate((valid) => {
				//验证通过
		          if (valid) {
				   this.axios.post('http://localhost:8083/user/login',this.loginForm
					).then((res)=>{
						
					   if(res.data.code==10001){
						   console.log(res.data);
						   //登录成功的用户 数据存储到vuex store中
						   this.$store.commit('login',res.data.result);
						   //路由替换   登录后到/Menu菜单页面
						   this.$router.replace({ path: '/Menu'});
					   }else{
						   alert('用户名或密码错误');
					   }
				   });
		          } else {
		            console.log('error submit!! 	');
		            return false;
		          }
		        });
		},
		resetForm(formName) {
			   this.$refs[formName].resetFields();
		 }
	}
}
</script>




<style>
	.el-row {
		margin-bottom: 20px;

		&:last-child {
			margin-bottom: 0;
		}
	}

	.login-box {
		background-repeat: no-repeat;
		background-size: 100%;
		height: 100%;
		position: fixed;
		width: 100%;
		margin-top: 0px;
		display: flex
	}
</style>
