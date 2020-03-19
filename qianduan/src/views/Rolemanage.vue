<template>
	<div>
		<el-row>
			<el-col :span="24">
				<el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDeletes()">批量删除</el-button>
			</el-col>
		</el-row>


		<el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">

			<el-table-column type="selection">
			</el-table-column>

			<el-table-column label="序号" prop="id" width="180">
			</el-table-column>

			<el-table-column label="职位" prop="nameZh" width="180">
			</el-table-column>

			<el-table-column label="菜单资源" prop="mnames" width="500">
			</el-table-column>


			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button size="mini" @click="handleDetails(scope.row)">详情</el-button>
					<el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
					<el-button size="mini" type="success" @click="handleMenus(scope.row)">授权menu资源</el-button>
					<el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--////////////////////分页组件//////////////////////////////////////////////////////-->
		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="current"
		 :page-sizes="sizes" :page-size="size" layout="total, sizes, prev, pager, next, jumper" :total="total">
		</el-pagination>


		<!--//////////////////////////////////授权menu资源开始//////////////////////////////////////-->

		<el-dialog title="menu资源授权" :visible.sync="menuDialogVisible" width="60%" center>
			<!--树形控件-->
			<!--树形控件-->
						<el-tree :data="treeData" :props="defaultProps" node-key="id" ref="tree" 
						:default-checked-keys="treeKeyNodes"
						 :default-expanded-keys="treeKeyNodes" 
						 show-checkbox>
						</el-tree>


			<span slot="footer" class="dialog-footer">
				<el-button type="primary" @click="handleSaveMenus()">确 定</el-button>
				<el-button @click="menuDialogVisible = false">取 消</el-button>
			</span>
		</el-dialog>

		<!--//////////////////////////////////授权menu资源结束//////////////////////////////////////-->

		<!--编辑menu开始-->
		<el-dialog title="编辑框" :visible.sync="EditDialogVisible" width="60%" center>
		
			<!--ref代表的是form引用-->
			<el-form ref="editRoleForm" :model="editRoleForm" label-width="100px">
			
				<el-form-item label="用户英文名称" prop="name">
					<el-input type="text" v-model="editRoleForm.name" placeholder="请输入用户名" clearable></el-input>
				</el-form-item>
			
			
				<el-form-item label="用户姓名" prop="nameZh">
					<el-input type="text" v-model="editRoleForm.nameZh" placeholder="请输入电话" clearable></el-input>
				</el-form-item>
				
				<el-form-item>
					
						<el-button type="primary" @click="handleSaveRole()">确 定</el-button>
						<el-button @click="EditDialogVisible = false">取 消</el-button>
				</el-form-item>
			</el-form>	
		</el-dialog>
		
		<!--//////////////////////////编辑menu结束/////////////////////////////////////////////////-->
			
		<!--//////////////////////////详情对话框/////////////////////////////////////////////////-->
	<el-dialog title="详情表" :visible.sync="detailsDialogVisible" width="60%" center>
	
		<!--ref代表的是form引用-->
		<el-form ref="editRoleForm" :model="editRoleForm" label-width="100px">
			
			<el-form-item label="ID" prop="id">
				<el-input type="text" v-model="editRoleForm.id" placeholder="请输入用户名" disabled="disabled" clearable></el-input>
			</el-form-item>
			
		
			<el-form-item label="用户英文名称" prop="name">
				<el-input type="text" v-model="editRoleForm.name" placeholder="请输入用户名" disabled="disabled" clearable></el-input>
			</el-form-item>
		
		
			<el-form-item label="用户姓名" prop="nameZh">
				<el-input type="text" v-model="editRoleForm.nameZh" placeholder="请输入电话" disabled="disabled"  clearable></el-input>
			</el-form-item>
			
			<el-form-item>
					<el-button @click="detailsDialogVisible = false">取 消</el-button>
			</el-form-item>
		</el-form>	
	</el-dialog>
	
	
	
	
		<!--//////////////////////////详情对话框结束/////////////////////////////////////////////////-->
	</div>

</template>

<script>
	export default {
		data() {
			return {
				disabled:false,
				detailsDialogVisible:false,
				editRoleForm:{
					id:0,
					name:'',
					nameZh:''
				},
				EditDialogVisible:false,
				rid:0,
				id:0,
				treeKeyNodes: [],
				treeData: [],
				defaultProps: {
					children: 'menus',
					label: 'name'
				},
				menuDialogVisible: false,
				multipleSelection: [],
				current: 1,
				size: 3,
				sizes: [3, 5, 8],
				total: 0,
				tableData: []
			}
		},
		created() {
			this.initData();
		},
		methods: {
			initTreeData() {
				this.axios.get('http://localhost:8083/menu/list').then((res) => {
					this.treeData = res.data.result;
				});
			},
			initData() {
				this.axios.get('http://localhost:8083/role/rolelist', {
					params: {
						current: this.current,
						size: this.size
					}
				}).then((res) => {
					//赋值表格数据
					this.tableData = res.data.result.records;
					//赋值总记录数
					this.total = res.data.result.total;
				})
			},
			handleEdit(row) {
				//console.log(row);
				
				let id = row.id;
				this.axios.get('http://localhost:8083/role/getById',{
					params:{
						id:id
					}
				}).then((res)=>{
					this.editRoleForm=res.data.result
				})
				
				
					
				this.EditDialogVisible=true;
			},
			handleDelete(row) {
				//确定框
				this.$confirm('你确定要删除你选中的数据吗?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					//发送ajax进行删除操作
					this.axios.get('http://localhost:8083/role/deletes', {
						params: {
							ids: row.id
						}
					}).then((res) => {
						//判断删除的结果
						if (res.data.result) {
							this.$message({
								type: 'success',
								message: '删除成功!'
							});
							//查询数据
							this.initData();
						} else {
							this.$message({
								type: 'warning',
								message: '删除失败'
							});
						}
					});


				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
			},
			handleSizeChange(val) {
				//重新赋值每页显示记录数
				this.size = val;
				//查询数据
				this.initData();
			},
			handleCurrentChange(val) {
				//重新赋值当前页
				this.current = val;
				//查询数据
				this.initData();
			},
			handleSelectionChange(val) {
				this.multipleSelection = val;
			},
			handleDeletes() {
				//判断是否有删除的记录
				if (this.multipleSelection.length > 0) {
					//确定框
					this.$confirm('你确定要删除你选中的数据吗?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
						//拼接删除的id字符串 比如：1,3,4形式
						var ids = this.multipleSelection.map((row) => {
							return row.id;
						}).join(",");
						//发送ajax进行删除操作
						this.axios.get('http://localhost:8083/role/deletes', {
							params: {
								ids: ids
							}
						}).then((res) => {
							//判断删除的结果
							if (res.data.result) {
								this.$message({
									type: 'success',
									message: '删除成功!'
								});
								//查询数据
								this.initData();
							} else {
								this.$message({
									type: 'warning',
									message: '删除失败'
								});
							}
						});


					}).catch(() => {
						this.$message({
							type: 'info',
							message: '已取消删除'
						});
					});
				} else {
					this.$message('请选择要删除的数据');
				}
			},
			handleMenus(row) {
				this.rid=row.id;
				//初始化tree
				this.initTreeData();
				//记录id存放
				this.treeKeyNodes = [];
				//获取本身拥有资源的id
				console.log(row.mids);
				if(row.mids != null ){
					row.mids.split(",").map((mid) => {
						if (!isNaN(mid)) {
							this.treeKeyNodes.push(parseInt(mid));
						}
					})
				}
				console.log(this.treeKeyNodes);
				//把要选中/回显的数据设置到tree上
				//打开对话框
				this.menuDialogVisible = true;
			},
			handleSaveMenus(){
				//获取选中的tree节点
				let mids= this.$refs.tree.getCheckedKeys();
				console.log(this.rid+"@"+mids);
				//拼接一下
				let mids_str =  mids.map((mid)=>{
						return mid;
					}).join(",");
			    console.log(mids_str);
				//发送axios请求
				this.axios.get('http://localhost:8083/menu_role/insert',{params:{
					rid:this.rid,
					mids:mids_str
				}}).then((res)=>{
					if(res.data.result){
						this.menuDialogVisible=false;
						this.$message({
							type: 'success',
							message: '授权成功'
						});
						this.initData();
					}else{
						this.$message({
							type: 'info',
							message: '授权失败'
						});
					}
				});
			},
			handleSaveRole(){
				this.axios.post('http://localhost:8083/role/update',this.editRoleForm).then((res)=>{
					if(res.data.result){
						this.EditDialogVisible=false;
						this.initData();
						this.$message({
							type: 'success',
							message: '修改成功'
						});
						
					}else{
						this.$message({
							type: 'info',
							message: '修改失败'
						});
					}
				})
			},
			handleDetails(row){
				this.detailsDialogVisible = true;
				let id = row.id;
				this.axios.get('http://localhost:8083/role/getById',{
					params:{
						id:id
					}
				}).then((res)=>{
					this.editRoleForm=res.data.result
				})
			}
		}
	}
</script>

<style>
</style>
