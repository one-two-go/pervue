<template>
	<div>

		<el-input style="width: 200px;" placeholder="请输入内容" v-model="name" clearable>
		</el-input>

		<el-input style="width: 200px;" placeholder="请输入价格" v-model="priceSmall" clearable>
		</el-input>--

		<el-input style="width: 200px;" placeholder="请输入价格" v-model="priceBig" clearable>
		</el-input>	


		<el-button type="danger" round @click="handleSearc()" size="mini">搜索</el-button>
		<el-button type="danger" round @click="handleDeletes()" size="mini">批量删除</el-button>
		<el-button type="danger" round @click="handleInsert()" size="mini">添加</el-button>

		<el-table :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">>

			<el-table-column type="selection" width="55">
			</el-table-column>


			<el-table-column prop="name" label="领导" width="180">
			</el-table-column>

			<el-table-column label="项目名称" width="180" prop="url">
			
			</el-table-column>

	<!-- 		地址显示
			<el-table-column label="地址" width="250">
				<template slot-scope="scope">
					{{scope.row.provinceName}}/{{scope.row.cityName}}/{{scope.row.districtName}}
				</template>
			</el-table-column> -->
			

			<el-table-column prop="price" label="价格" width="100">
			</el-table-column>

			<el-table-column label="日期" width="100">
				<template slot-scope="scope">
					{{scope.row.created | formatTimer}}
				</template>
			</el-table-column>

			<el-table-column prop="mname" label="促销" width="100">
			</el-table-column>

			<el-table-column label="操作">
				<template slot-scope="scope">
					<!-- <el-button type="danger" round @click="marketDelets(scope.row)" size="mini">活动</el-button> -->
					<!-- <el-button type="danger" round @click="handleDelets(scope.row)" size="mini">删除</el-button> -->
					
					<el-button type="danger" round @click="handleEdit(scope.row)" size="mini">编辑</el-button>
					<el-button type="danger" round @click="handleDetails(scope.row)" size="mini">详情</el-button>
				</template>
			</el-table-column>

		</el-table>


		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="current"
		 :page-sizes="[3,6,9]" :page-size="size" layout="total, sizes, prev, pager, next, jumper" :total="total">
		</el-pagination>



		<!-- 添加的商品对话框   -->
		<el-dialog :visible.sync="insertDialogVisible" width="30%">

			<el-form ref="insertGoodsArr" :model="insertGoodsArr" :rules="rulesGoods" label-width="80px">

				<el-form-item label="项目名称" prop="name">
					<el-input v-model="insertGoodsArr.name" placeholder="请输入项目名称"></el-input>
				</el-form-item>

			<!-- 	实现图片的上传
				<el-form-item label="图片">
					<el-upload class="avatar-uploader" action="http://localhost:8083/upload" :show-file-list="false" :on-success="handleAvatarSuccess"
					 :before-upload="beforeAvatarUpload">
						<img v-if="imageUrl" :src="imageUrl" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item> -->

				<el-form-item label="价格" prop="price">
					<el-input v-model="insertGoodsArr.price" placeholder="请输入价格"></el-input>
				</el-form-item>


						<el-form-item label="厂家">
					<div style="margin: 15px 0;"></div>
					<el-checkbox-group v-model="checkedRoles" @change="handleCheckedMarketChange">
						<el-checkbox v-for="mar in markets" :label="mar.id">{{mar.mname}}</el-checkbox>
					</el-checkbox-group>
				</el-form-item>

				<!-- <el-form-item label="用户地址">
					<el-cascader v-model="values" :options="options" @change="handleChangeCity"></el-cascader>
				</el-form-item> -->

				<el-form-item>
					<el-button @click="insertDialogVisible = false">取 消</el-button>
					<el-button type="primary" @click="insertGoods('insertGoodsArr')">确 定</el-button>
				</el-form-item>
			</el-form>

		</el-dialog>
		<!-- 添加的商品对话框结束   -->

		<!-- 编辑对话框-->
		<el-dialog :visible.sync="updateDialogVisible" width="30%">

			<el-form ref="updateGoodsArr" :model="updateGoodsArr" label-width="80px">

				<el-form-item label="商品名" prop="name">
					<el-input v-model="updateGoodsArr.name" placeholder="请输入姓名"></el-input>
				</el-form-item>

				<!--实现图片的上传-->
				<el-form-item label="图片">
					<el-upload class="avatar-uploader" action="http://localhost:8083/upload" :show-file-list="false" :on-success="handleAvatarSuccess"
					 :before-upload="beforeAvatarUpload">
						<img v-if="imageUrl" :src="imageUrl" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item>

				<el-form-item label="价格" prop="price">
					<el-input v-model="updateGoodsArr.price" placeholder="请输入价格"></el-input>
				</el-form-item>

				<el-form-item label="用户地址">
					<el-cascader v-model="values" :options="options" @change="updateHandleChangeCity"></el-cascader>
				</el-form-item>

				<el-form-item>
					<el-button @click="updateDialogVisible = false">取 消</el-button>
					<el-button type="primary" @click="updateGoods()">确 定</el-button>
				</el-form-item>
			</el-form>

		</el-dialog>


		<!-- 编辑对话框-->



		<!-- 详情-->
		<el-dialog :visible.sync="desDialogVisible" width="30%">

			<el-form ref="updateGoodsArr" :model="updateGoodsArr" label-width="80px">

				<el-form-item label="分管领导" prop="url">
					<el-input v-model="updateGoodsArr.name" placeholder="请输入姓名" :disabled="disabled"></el-input>
				</el-form-item>
				<el-form-item label="项目名称" prop="url">
					<el-input v-model="updateGoodsArr.url" placeholder="" :disabled="disabled"></el-input>
				</el-form-item>

				<!--实现图片的上传-->
				<!-- <el-form-item label="图片">
					<el-upload class="avatar-uploader" action="http://localhost:8083/upload" :show-file-list="false" :on-success="handleAvatarSuccess"
					 :before-upload="beforeAvatarUpload" :disabled="disabled">
						<img v-if="imageUrl" :src="imageUrl" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item> -->

				<el-form-item label="价格" prop="price">
					<el-input v-model="updateGoodsArr.price" placeholder="请输入价格" :disabled="disabled"></el-input>
				</el-form-item>


						<el-form-item label="促销">
					<div style="margin: 15px 0;"></div>
					<el-checkbox-group v-model="checkedRoles" @change="handleCheckedMarketChange">
						<el-checkbox v-for="mar in markets" :label="mar.id">{{mar.mname}}</el-checkbox>
					</el-checkbox-group>
				</el-form-item>

				<!-- <el-form-item label="用户地址">
					<el-cascader v-model="values" :options="options" @change="updateHandleChangeCity" :disabled="disabled"></el-cascader>
				</el-form-item> -->

				<el-form-item>
					<el-button @click="desDialogVisible = false">取 消</el-button>
				</el-form-item>
			</el-form>

		</el-dialog>
		<!-- 结束-->





		<!-- 活动-->
		<el-dialog :visible.sync="marketDialogVisible" width="30%">

			<el-form ref="markForm" :model="markForm" label-width="100px">

				<el-form-item label="销售">
					<el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
					<div style="margin: 15px 0;"></div>
					<el-checkbox-group v-model="checkedRoles" @change="handleCheckedMarketChange">
						<el-checkbox v-for="mar in markets" :label="mar.id">{{mar.mname}}</el-checkbox>
					</el-checkbox-group>
				</el-form-item>

				<el-form-item>
					<el-button type="primary" size="mini" @click="handleMarkForm()">保存</el-button>
				</el-form-item>

			</el-form>
		</el-dialog>
		<!-- 活动-->





	</div>
</template>

<script>
	export default {
		data() {
			return {
				timer: new Date(),
				gid: 0,
				markForm: {},
				marketDialogVisible: false, // 活动的添加
				disabled: true,
				desDialogVisible: false,
				updateGoodsArr: {
					name: '',
					url: '',
					price: '',
					province: 0,
					city: 0,
					district: 0,
					ids: ''
				},
				updateDialogVisible: false,
				rulesGoods: { //效验
					name: [{
							required: true,
							message: '请输入活动名称',
							trigger: 'blur'
						},
						{
							min: 3,
							max: 5,
							message: '长度在 3 到 5 个字符',
							trigger: 'blur'
						}
					],
					price: [{
						required: true,
						message: '请输入价格',
						trigger: 'blur'
					}, ]
				},
				values: [],
				options: [], // 三级联动的数据
				checkedRoles: [], //默认回显的数据
				markets: [],
				checkAll: false, //  
				isIndeterminate: true, // 控制 多选框样式
				imageUrl: '', //头像上传成功的地址
				insertGoodsArr: {
					name: '',
					url: '',
					price: '',
					province: 0,
					city: 0,
					district: 0,
					ids: ''
				},
				insertDialogVisible: false,
				tableData: [],
				current: 0,
				size: 3,
				total: 0,
				name: '',
				priceSmall: '',
				priceBig: '',
				selection: [] // 记录全选 反选
			}
		},
		created() {
			this.initData();
			this.initMarket();
			this.initCity();
		},
		filters: {
			formatTimer: function(value) {
				let date = new Date(value);
				let y = date.getFullYear();
				let MM = date.getMonth() + 1;
				MM = MM < 10 ? "0" + MM : MM;
				let d = date.getDate();
				d = d < 10 ? "0" + d : d;
				let h = date.getHours();
				h = h < 10 ? "0" + h : h;
				let m = date.getMinutes();
				m = m < 10 ? "0" + m : m;
				let s = date.getSeconds();
				s = s < 10 ? "0" + s : s;
				return y + "-" + MM + "-" + d + " " + h + ":" + m;
			}
		},
		mounted() {
			let _this = this; // 声明一个变量指向Vue实例this，保证作用域一致
			this.timer1 = setInterval(() => {
				_this.timer = new Date(); // 修改数据date
			}, 1000)
		},
		beforeDestroy() {
			if (this.timer1) {
				clearInterval(this.timer1); // 在Vue实例销毁前，清除我们的定时器
			}
		},
		methods: {
			initCity() {
				this.axios.post('http://localhost:8083/nation/list').then((res) => {
					this.options = res.data.result;
				})
			},
			initMarket() {
				this.axios.get('http://localhost:8083/qlmarket/marketList').then((res) => {
					this.markets = res.data.result;
				})
			},
			initData() {
				this.axios.get('http://localhost:8083/qlgoods/goodsList', {
					params: {
						current: this.current,
						size: this.size,
						name: this.name,
						priceSmall: this.priceSmall,
						priceBig: this.priceBig
					}
				}).then((res) => {
					console.log()
					this.tableData = res.data.result.records;
					this.total = res.data.result.total
				})
			},
			handleSizeChange(row) {
				this.size = val;
				this.initData();
			},
			handleCurrentChange(val) {
				this.current = val;
				this.initData();
			},
			handleSearc() {
				this.initData();
			},
			handleSelectionChange(val) {
				this.selection = val;
				console.log(this.selection)
			},
			handleDeletes(row) {
				// this.selection 存储的是每一个选中的对象数组
				let ids = this.selection.map((row) => {
					return row.gid; // 遍历每一个对象的id
				}).join(",");

				if (ids != null) {
					this.$confirm('此操作将永久删除该商品, 是否继续?', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
						this.axios.get('http://localhost:8083/qlgoods/deletes', {
							params: {
								ids: ids
							}
						}).then((res) => {
							if (res.data.result) {
								this.$message({
									type: 'success',
									message: '删除成功!'
								});
								this.initData();
							} else {
								this.$message({
									type: 'info',
									message: '删除失败'
								});
							}
						})
					}).catch(() => {
						this.$message({
							type: 'info',
							message: '已取消删除'
						});
					});
				} else {
					alert("不能为空")
				}
			},
			handleDelets(row) { // 单删

				let ids = row.gid;

				this.$confirm('此操作将永久删除该商品, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.axios.get('http://localhost:8083/qlgoods/deletes', {
						params: {
							ids: ids
						}
					}).then((res) => {
						if (res.data.result) {
							this.$message({
								type: 'success',
								message: '删除成功!'
							});
							this.initData();
						} else {
							this.$message({
								type: 'info',
								message: '删除失败'
							});
						}
					})
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
			},
			handleInsert() { // 添加
				this.insertDialogVisible = true
			},
			handleAvatarSuccess(res, file) { // 图片上传
				this.imageUrl = URL.createObjectURL(file.raw);

				if (this.insertDialogVisible) {
					this.insertGoodsArr.url = res.result; // 将上传的地址赋值到添加的属性中
					console.log("+++++++++++++++++++++" + this.insertGoodsArr.url)
				} else if (this.updateDialogVisible) {
					this.updateGoodsArr.url = res.result;
				}

			},
			beforeAvatarUpload(file) {
				const isJPG = file.type === 'image/jpeg';
				const isLt2M = file.size / 1024 / 1024 < 2;

				// if (!isJPG) {
				// 	this.$message.error('上传头像图片只能是 JPG 格式!');
				// }
				if (!isLt2M) {
					this.$message.error('上传头像图片大小不能超过 2MB!');
				}
				return isJPG && isLt2M;
			},
			handleCheckedMarketChange(value) {
				console.log("+++++++" + value)
				let checkedCount = value.length;
				this.checkAll = checkedCount === this.markets.length;
				this.isIndeterminate = checkedCount > 0 && checkedCount < this.markets.length;

				console.log("@@@@" + this.checkedRoles)
			},
			handleChangeCity(val) {
				if (val != null && val.length > 0) {
					this.insertGoodsArr.province = val[0];
					this.insertGoodsArr.city = val[1];
					this.insertGoodsArr.district = val[2];
				}
			},
			insertGoods(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {

						this.axios.post('http://localhost:8083/qlgoods/insert', this.insertGoodsArr).then((res) => {
							if (res.data.result) {
								this.insertDialogVisible = false;
								this.initData();
								this.$message({
									type: 'success',
									message: '添加成功!'
								});
							} else {
								this.insertDialogVisible = false;
								this.$message({
									type: 'success',
									message: '添加失败!'
								});
							}
						})

					} else {
						console.log('不成功');
						return false;
					}
				});
			},
			handleEdit(row) { // 打开对话框  回显
				this.updateDialogVisible = true;
				let gid = row.gid;
				// 数据克隆 用于将源对象的所有可枚举属性复制到目标对象中。
				this.updateGoodsArr = Object.assign({}, row);

				this.values = [];


				//省市区的回显
				if (row.province) {
					this.values.push(row.province);
					this.values.push(row.city);
					this.values.push(row.district);
				}

				this.imageUrl = row.url;
			},
			updateHandleChangeCity(val) {
				//判断是否为null
				if (val != null && val.length > 0) {
					//省赋值
					this.updateGoodsArr.province = val[0];
					//市赋值
					this.updateGoodsArr.city = val[1];
					//区赋值
					this.updateGoodsArr.district = val[2];
				}
			},
			updateGoods() { // 编辑
				this.axios.post('http://localhost:8083/qlgoods/update', this.updateGoodsArr).then((res) => {
					if (res.data.result) {
						this.updateDialogVisible = false;
						this.initData();
						this.$message({
							type: 'success',
							message: '修改成功!'
						});
					} else {
						this.updateDialogVisible = false;
						this.$message({
							type: 'success',
							message: '修改失败!'
						});
					}
				})
			},
			handleDetails(row) { // 详情
				this.desDialogVisible = true;

				this.desDialogVisible = Object.assign({}, row);

				this.updateGoodsArr.name = row.name;
				this.updateGoodsArr.price = row.price;

				this.values = [];


				//省市区的回显
				if (row.province) {
					this.values.push(row.province);
					this.values.push(row.city);
					this.values.push(row.district);
				}

				this.imageUrl = row.url;
			},
			marketDelets(row) {

				this.gid = row.gid;

				if (row.ids != null && row.ids != '') {
					//默认被选中的数据 //回显的数据
					this.checkedRoles = row.ids.split(",").map((x) => {
						if (!isNaN(x)) {
							return parseInt(x); //数字转换的过程
						}
					});
					console.log(this.checkedRoles);
				}
				this.marketDialogVisible = true;
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
			handleMarkForm() {

				let gid = this.gid;

				let mid = this.checkedRoles.map((mid) => {
					return mid;
				}).join(",");

				//发送请求
				this.axios.get('http://localhost:8083/qlcenter/insert', {
					params: {
						gid: gid,
						mid: mid
					}
				}).then((res) => {

					if (res.data.result) {
						this.initData();
						this.marketDialogVisible = false;
					}


				})
				this.initData();
				this.marketDialogVisible = false;
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
