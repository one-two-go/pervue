<template>
	<div>

		<el-row>
			<el-col :span="8">
				<el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDeletes()">批量删除</el-button>
			</el-col>
			<el-col :span="8">
				<el-button type="danger" size="mini" icon="el-icon-edit" @click="handleOpenUserInsertDialog()">添加用户</el-button>
			</el-col>
			<el-col :span="8">
				<el-popover placement="right" width="900px" trigger="click">

					<el-row>
						
						<el-col :span="10">
							用户名称:<el-input  style="width: 75%;" v-model="searchParam.name" placeholder="请输入名称"></el-input>
						</el-col>
						
						<el-col :span="10">
							用户电话:<el-input  style="width: 75%;" v-model="searchParam.phone" placeholder="请输入电话号码"></el-input>
						</el-col>
						
						<el-col :span="4">
							<el-button type="primary" size="medium" icon="el-icon-search" @click="initDate()">搜索</el-button>
						</el-col>
					</el-row>
					
					<el-button type="danger" size="mini" icon="el-icon-search" slot="reference" style="width: 80px;">搜索</el-button>
				</el-popover>
			</el-col>
		</el-row>


		<el-table :data="tableDate" @selection-change="selectionChange">
			<el-table-column type="selection" width="55"></el-table-column>
			<el-table-column prop="id" label="ID"></el-table-column>
			<el-table-column label="头像">
				<template slot-scope="scope">
					<el-image style="width: 60px; height: 60px" :src="scope.row.userface"></el-image>
				</template>
			</el-table-column>
			<el-table-column prop="name" label="姓名"></el-table-column>

			<el-table-column label="详细地址信息">
				<template slot-scope="scope">
					{{scope.row.provinceName}}/{{scope.row.cityName}}/{{scope.row.districtName}}
				</template>
			</el-table-column>

			<el-table-column prop="rnameZhs" label="用户角色"></el-table-column>

			<el-table-column prop="phone" label="电话"></el-table-column>

			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button type="primary" size="mini" style="width:75px;height: 25px;" icon="el-icon-edit" @click="handleRoles(scope.row)"
					 round>授权</el-button>
					<el-button type="info" size="mini" style="width:70px;height: 25px;" icon="el-icon-edit" @click="handleEdit(scope.row)"
					 round>编辑</el-button>
					<el-button type="danger" style="width:75px; height: 25px;" size="mini" icon="el-icon-delete" @click="handleDelete(scope.row)"
					 round>删除</el-button>
					<el-button type="primary" style="width:70px;height: 25px;" icon="el-icon-star-off" size="mini" @click="handleDetails(scope.row)"
					 round>详情</el-button>
				</template>
			</el-table-column>
		</el-table>
		<!--   
		jumper 前往第几页
		total 显示总计数据
		sizes 每页显示的数据条数
		current-page 当前页
		-->
		<el-pagination background 
		layout="prev, pager, next,jumper,total,sizes" 
		:total="total" 
		:current-page="current"
		 :page-size="size" 
		 :page-sizes="sizes" @current-change="handleCurrentChange" @size-change="handleSizeChange">
		</el-pagination>
		<!--
		 
		 
		 -->
		<!--/////////////////////添加用户对话框开始/////////////////////-->
		<el-dialog :visible.sync="insertUserDialog" title="用户添加" center>
			<!--ref代表的是form引用-->
			<el-form ref="insertUserForm" :model="insertUserForm" :rules="insertUserFormRules" label-width="100px">

				<el-form-item label="用户名称" prop="name">
					<el-input type="text" v-model="insertUserForm.name" placeholder="请输入用户名" clearable></el-input>
				</el-form-item>

				<!--实现图片的上传-->
				<el-form-item label="用户头像">
					<el-upload class="avatar-uploader" action="http://localhost:8083/upload" :on-success="handleAvatarSuccess"
					 :before-upload="beforeAvatarUpload" :show-file-list="false">
						<img v-if="imageUrl" :src="imageUrl" class="avatar" />
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item>


				<el-form-item label="用户电话" prop="phone">
					<el-input type="text" v-model="insertUserForm.phone" placeholder="请输入电话" clearable></el-input>
				</el-form-item>

				<el-form-item label="用户地址" prop="address">
					<!-- <el-input type="text" v-model="insertUserForm.address" placeholder="请输入用户名" clearable></el-input> -->

					<el-cascader :options="options" 
					style="width: 100%;"
					 @change="cascaderInsertChange"></el-cascader>

				</el-form-item>

				<el-form-item label="用户性别" prop="sex">
					<el-radio-group v-model="insertUserForm.sex">
						<el-radio :label="1">男</el-radio>
						<el-radio :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>

				<!--提交按钮-->
				<el-form-item>
					<el-button type="primary" size="mini" @click="handleInsertForm('insertUserForm')">保存</el-button>
					<el-button type="info" size="mini" @click="resetForm('insertUserForm')">重置</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>
		<!--/////////////////////对话框结束/////////////////////-->

		<!--/////////////////////////用户角色授权///////////////////////////////////-->
		<el-dialog :visible.sync="roleDialog" title="用户角色授权" center>
			<!--ref代表的是form引用-->
			<el-form ref="roleForm" :model="roleForm" label-width="100px">

				<el-form-item label="角色列表">
					<el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
					<div style="margin: 15px 0;"></div>
					<el-checkbox-group v-model="checkedRoles" @change="handleCheckedRoleChange">
						<el-checkbox v-for="role in roles" :label="role.id">{{role.nameZh}}</el-checkbox>
					</el-checkbox-group>
				</el-form-item>

				<!--提交按钮-->
				<el-form-item>
					<el-button type="primary" size="mini" @click="handleRoleForm()">保存</el-button>
					<el-button type="info" size="mini" @click="resetForm('roleForm')">重置</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>
		<!--/////////////////////////用户角色授权///////////////////////////////////-->

		<!--///////////////////////编辑对话框///////////////////////////////////-->
		<el-dialog :visible.sync="editUserDialog" title="用户编辑" center>
			<!--ref代表的是form引用-->
			<el-form ref="editUserForm" :model="editUserForm" label-width="100px">

				<el-form-item label="用户名称" prop="name">
					<el-input type="text" v-model="editUserForm.name" placeholder="请输入用户名" clearable></el-input>
				</el-form-item>

				<!--实现图片的上传-->
				<el-form-item label="用户头像">
					<el-upload class="avatar-uploader" action="http://localhost:8083/upload" :on-success="handleAvatarSuccess"
					 :before-upload="beforeAvatarUpload" :show-file-list="false">
						<img v-if="imageUrl" :src="imageUrl" class="avatar" />
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item>


				<el-form-item label="用户电话" prop="phone">
					<el-input type="text" v-model="editUserForm.phone" placeholder="请输入电话" clearable></el-input>
				</el-form-item>

				<el-form-item label="用户详细地址">
					<!-- <el-input type="text" v-model="insertUserForm.address" placeholder="请输入用户名" clearable></el-input> -->

					<el-cascader :options="options" v-model="values" style="width: 100%;" @change="cascaderUpdateChange"></el-cascader>

				</el-form-item>

				<el-form-item label="用户性别" prop="sex">
					<el-radio-group v-model="editUserForm.sex">
						<el-radio :label="1">男</el-radio>
						<el-radio :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>

				<!--提交按钮-->
				<el-form-item>
					<el-button type="primary" size="mini" @click="handleEditForm()">保存</el-button>
					<el-button type="info" size="mini" @click="resetForm('editUserDialog')">重置</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>
		<!--///////////////////////编辑对话框///////////////////////////////////-->




		<!--///////////////////////详情对话框开始///////////////////////////////////-->
		<el-dialog :visible.sync="detailsUserDialog" title="用户编辑" center>
			<!--ref代表的是form引用-->
			<el-form ref="editUserForm" :model="editUserForm" label-width="100px">

				<el-form-item label="用户名称" prop="name">
					<el-input type="text" v-model="editUserForm.name" :disabled="disabled" placeholder="请输入用户名" clearable></el-input>
				</el-form-item>

				<!--实现图片的上传-->
				<el-form-item label="用户头像">
					<el-upload class="avatar-uploader" action="http://localhost:8083/upload" :on-success="handleAvatarSuccess"
					 :before-upload="beforeAvatarUpload" :show-file-list="false" :disabled="disabled">
						<img v-if="imageUrl" :src="imageUrl" class="avatar" />
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item>


				<el-form-item label="用户电话" prop="phone">
					<el-input type="text" v-model="editUserForm.phone" placeholder="请输入电话" :disabled="disabled" clearable></el-input>
				</el-form-item>

				<el-form-item label="用户详细地址">
					<!-- <el-input type="text" v-model="insertUserForm.address" placeholder="请输入用户名" clearable></el-input> -->

					<el-cascader :options="options" v-model="values" style="width: 100%;" @change="cascaderUpdateChange" :disabled="disabled"></el-cascader>

				</el-form-item>

				<el-form-item label="用户性别" prop="sex">
					<el-radio-group v-model="editUserForm.sex" :disabled="disabled">
						<el-radio :label="1">男</el-radio>
						<el-radio :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>

				<el-form-item>
					<el-button type="primary" size="mini" @click="closeDetailsUserDialog()">关闭</el-button>
				</el-form-item>

			</el-form>
		</el-dialog>
		<!--///////////////////////详情对话框结束///////////////////////////////////-->

	</div>
</template>

<script>
	export default {
		data() {
			return {
				searchParam:{//搜索的绑定
					name:'',
					phone:''
				},
				disabled: true, //禁用
				detailsUserDialog: false, //详情对话框控制
				values: [],
				editUserForm: { //编辑的form对象
					id: '',
					name: '',
					sex: 0,
					phone: '',
					address: '',
					userface: "",
					province: 0,
					city: 0,
					district: 0
				},
				editUserDialog: false, //编辑对话框打开
				uid: 0,
				roles: [], //角色数组
				checkedRoles: [], //默认回显的数据
				checkAll: false,
				isIndeterminate: false, //设置 indeterminate 状态，只负责样式控制
				roleDialog: false, //角色的对话框
				roleForm: {}, //角色表单
				options: [], //三级联动的数据
				imageUrl: '', //头像上传成功的地址
				tableDate: [], //表格显示的数据
				selection: [], //记录全选/反选的结果
				total: 0, //总记录数
				size: 3, //每页显示的条目数  默认值
				current: 1, //当前页
				sizes: [3, 5, 8],
				insertUserDialog: false,
				insertUserForm: {
					name: 'ql',
					sex: 1,
					phone: '18622101513',
					address: '北京海定区',
					userface: "",
					province: 0,
					city: 0,
					district: 0
				},
				insertUserFormRules: {
					name: [{
						required: true,
						message: '用户名不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		created() {
			this.initDate(); //初始化数据
			this.initCascader(); //初始化三级联动的数据
			this.initRoles(); //初始化角色
		},
		methods: {
			initRoles() {
				this.axios.get('http://localhost:8083/role/list').then((res => {
					this.roles = res.data.result; // 给对象数组赋值
				}))
			},
			initCascader() { // 处理三级联动的数据
				this.axios.post('http://localhost:8083/nation/list').then((res) => {
					this.options = res.data.result;
				})
			},
			initDate() {
				//axios的请求方式
				this.axios.get('http://localhost:8083/user/list', {
					params: {
						current: this.current,
						size: this.size,
						name: this.searchParam.name,
						phone: this.searchParam.phone
					}
				}).then((response) => {
					console.log(response);
					this.tableDate = response.data.result.records;
					this.total = response.data.result.total
				})
			}, // 批量删除
			handleDeletes(row) {

				//console.log("@@@@@"+row.id);

				let ids = this.selection.map((row) => {
					return row.id;
				}).join(",");

				this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					//axios方式提交到/user/delete
					this.axios.get('http://localhost:8083/user/delete', {
						params: {
							ids: ids
						}
					}).then((response) => {

						if (response.data.result) {
							this.$message({
								showClose: true,
								message: '删除数据成功',
								type: 'success'
							});
							this.initDate();
						} else {
							this.$message({
								showClose: true,
								message: '删除失败了',
								type: 'error'
							});
						}
						console.log(response.data.result);
					})
				})
			},
			handleDelete(row) {
				console.log(row);
			},
			selectionChange(val) {
				this.selection = val;
			},
			handleCurrentChange(val) {
				this.current = val; //记录改变的当前页
				this.initDate();
			},
			handleSizeChange(val) {
				this.size = val;
				this.initDate();
			},
			handleOpenUserInsertDialog() {
				this.imageUrl = '';
				this.insertUserDialog = true;
			},
			resetForm(formName) {
				//重置表单
				this.$refs[formName].resetFields();
			},
			handleInsertForm(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						console.log("表单提交");
						this.axios.post('http://localhost:8083/user/insert', this.insertUserForm).then((res) => {
							if (res.data.result) {
								//消息提示
								this.$message({
									showClose: true,
									message: '数据更新成功',
									type: 'success'
								});
								//关闭对话框
								this.insertUserDialog = false;
								//重新加载数据
								this.initDate();
								//重置表单
								this.resetForm(formName);
							} else {
								//消息提示
								this.$message({
									showClose: true,
									message: '数据更新失败',
									type: 'success'
								});
							}
						})
					} else {
						console.log('验证不通过!!!');
						return false;
					}
				});
			},
			handleAvatarSuccess(res, file) {
				this.imageUrl = URL.createObjectURL(file.raw);
				if (this.editUserDialog) {
					this.editUserForm.userface = res.result;
				} else if (this.insertUserDialog) {
					//注意：不像前面写res.data.result
					this.insertUserForm.userface = res.result;
				}
			},
			beforeAvatarUpload(file) {
				/*
							        const isJPG = file.type === 'image/jpeg';
							        const isLt2M = file.size / 1024 / 1024 < 2;
							
							        if (!isJPG) {
							          this.$message.error('上传头像图片只能是 JPG 格式!');
							        }
							        if (!isLt2M) {
							          this.$message.error('上传头像图片大小不能超过 2MB!');
							        }
							        return isJPG && isLt2M;
				*/
			},
			cascaderInsertChange(val) {
				if (val != null && val.length > 0) {
					//省
						this.insertUserForm.province = val[0],

						//市
						this.insertUserForm.city = val[1],

						//区
						this.insertUserForm.district = val[2]
				}
			},
			handleRoles(row) { //打开角色的对话框
				console.log(row.rids);

				this.uid = row.id; //记录用户的id值
				if (row.rids != null && row.rids != '') {
					//默认被选中的数据 //回显的数据
					this.checkedRoles = row.rids.split(",").map((x) => {
						if (!isNaN(x)) {
							return parseInt(x); //数字转换的过程
						}
					});
					console.log(this.checkedRoles);
				}
				this.roleDialog = true;
			},
			handleRoleForm() {

				console.log("@@@" + this.uid);
				console.log("@@@" + this.checkedRoles.length)

				let rids = this.checkedRoles.map((rid) => {
					return rid;
				}).join(",");

				console.log(rids)


				//发送请求
				this.axios.get('http://localhost:8083/user_role/insert', {
					params: {
						uid: this.uid,
						rids: rids
					}
				}).then((res) => {
					this.roleDialog = false;
					this.initDate(); //初始化数据
				})

				//保存后关闭对话框
				this.roleDialog = false;
			},
			handleCheckAllChange(val) {
				if (val) {
					if (this.roles != null && this.roles.length > 0) {
						this.roles.map((role) => {
							let id = role.id;
							this.checkedRoles.push(id);
						})
					}
				} else {
					this.checkedRoles = [];
				}

				this.isIndeterminate = false;
			},
			handleCheckedRoleChange(value) {
				console.log(value)
				let checkedCount = value.length;
				this.checkAll = checkedCount === this.roles.length;
				this.isIndeterminate = checkedCount > 0 && checkedCount < this.roles.length;
			},
			handleEdit(row) { //打开关于编辑的对话框

				this.editUserDialog = true;
				// 数据克隆 用于将源对象的所有可枚举属性复制到目标对象中。
				this.editUserForm = Object.assign({}, row);


				//	this.editUserForm = row;

				this.values = []; //控制三及联动的数据

				//省市区的回显
				if (row.province) {
					this.values.push(row.province);
					this.values.push(row.city);
					this.values.push(row.district);
				}

				console.log("@@@@@" + this.editUserForm.id);
				this.imageUrl = row.userface;
			},
			cascaderUpdateChange(val) { // 给编辑添加三级联动
				//判断是否为null
				if (val != null && val.length > 0) {
					//省赋值
					this.editUserForm.province = val[0];
					//市赋值
					this.editUserForm.city = val[1];
					//区赋值
					this.editUserForm.district = val[2];
				}
			},
			handleEditForm() {
				this.axios.post('http://localhost:8083/user/update', this.editUserForm).then((res) => {
					if (res.data.result) {

						//消息提示
						this.$message({
							message: '恭喜你，用户编辑成功',
							type: 'success'
						});
						//关闭对话框
						this.editUserDialog = false;
						//重置表单
						this.initDate();

						//重新加载数据
					} else {
						this.$message.error('用户编辑失败了！');
					}
				})
			},
			handleDelete(row) { //单删的方法
				//console.log("@@@@@@"+row.id)
				let ids = row.id;

				this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					//axios方式提交到/user/delete
					this.axios.get('http://localhost:8083/user/delete', {
						params: {
							ids: ids
						}
					}).then((response) => {

						if (response.data.result) {
							this.$message({
								showClose: true,
								message: '删除数据成功',
								type: 'success'
							});
							this.initDate();
						} else {
							this.$message({
								showClose: true,
								message: '删除失败了',
								type: 'error'
							});
						}
						console.log(response.data.result);
					})
				})
			},
			handleDetails(row) { //打开详情对话框
				this.detailsUserDialog = true;

				this.editUserForm = row;

				this.values = []; //控制三及联动的数据

				//省市区的回显
				if (row.province) {
					this.values.push(row.province);
					this.values.push(row.city);
					this.values.push(row.district);
				}

				console.log("@@@@@" + this.editUserForm.id);
				this.imageUrl = row.userface;

			},
			closeDetailsUserDialog() {
				this.detailsUserDialog = false

			}
		}
	}
</script>

<style>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 100px;
		height: 100px;
		line-height: 100px;
		text-align: center;
	}

	.avatar {
		width: 100px;
		height: 100px;
		display: block;
	}
</style>
