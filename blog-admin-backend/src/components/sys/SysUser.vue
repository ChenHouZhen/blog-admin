<template>
    <div style="margin-top:20px;">
        <el-card>
            <el-form style="width:100%" :model="query_form" :inline="true">
                <div>
                    <el-form-item label="用户名">
                        <el-input v-model="query_form.user_name"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号">
                        <el-input v-model="query_form.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="开始时间">
                        <el-date-picker v-model="query_form.start_time" placeholder="请选择时间"
                        format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="结束时间">
                        <el-date-picker v-model="query_form.end_time" placeholder="请选择时间"
                        format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd 23:59:59"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="">
                        <el-switch v-model="query_form.status" active-text='正常' inactive-text='禁用'
                        active-value='1' inactive-value='0' inactive-color="#ff4949"></el-switch>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="queryTable">查询</el-button>
                        <el-button type="primary" @click="resetQueryForm">重置</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </el-card>

        <el-card style="margin-top:10px;">
            <div style="display:flex;">
                <el-button type="primary" icon="el-icon-circle-plus-outline" @click="showAddUserForm">新增</el-button>
            </div>
             <el-divider></el-divider>
            <el-table :data="table_data" style="width:100%" v-loading="table_loading">
                    <el-table-column prop="userId" label="用户ID"></el-table-column>
                    <el-table-column prop="username" label="用户名"></el-table-column>
                    <el-table-column prop="email" label="邮箱"></el-table-column>
                    <el-table-column prop="mobile" label="手机号"></el-table-column>
                    <el-table-column prop="deptName" label="部门"></el-table-column>
                    <el-table-column prop="createTime" label="创建时间"></el-table-column>
                    <el-table-column label="头像">
                        <template slot-scope="scope">
                            <i @click="view_avatar(scope.row.avatar)" style="cursor: pointer;">
                                <el-avatar size="small" :src="scope.row.avatar" shape='circle'></el-avatar>
                            </i>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" fixed='right'>
                        <template slot-scope="scope" v-if="operation">
                            <el-button @click="openEditUserForm(scope.row)" icon="el-icon-edit" size="mini">编辑</el-button>
                            <el-popover ref="popover{{$index}}" width="160"  v-model="scope.row.visible">
                                <p>确定禁用吗？</p>
                                <div style="text-align: right; margin: 0">
                                    <el-button size="mini" type="text" @click="closeComfirmForbid(scope.row)" >取消</el-button>
                                    <el-button type="primary" size="mini" @click="handleForbid(scope.row)">确定</el-button>
                                </div>
                            </el-popover>
                            <el-button v-popover:popover{{$index} type="danger" size="mini" icon="el-icon-delete" 
                            @click="openComfirmForbid(scope.row)" :loading="forbid_user_loading">禁用</el-button>
                        </template>
                        
                    </el-table-column>
                </el-table>
                <el-pagination background layout="total, sizes,prev, pager, next" :current-page.sync="query_form.page.current_page" 
                :total="query_form.page.total_size" @current-change="handleCurrentChange"
                :page-sizes="[5, 10, 50, 100]" @size-change="handleSizeChange" ></el-pagination>
        </el-card>
 
        <div class="custom_avatar_view" v-if="is_show_avatar">
            <span class="el-image-viewer__btn el-image-viewer__close" style="top: 100px;right:120px">
                <i @click="close_avatar_img" class="el-icon-circle-close"></i>
            </span>
            <div class="custom_avatar_view_image">
                <img :src="show_avatar"/>
            </div>
        </div>

        <!-- 新增表单 -->
        <el-dialog title="新增用户" :visible.sync="is_show_add_user_form" width="30%">
            <el-form :model="user_form" label-width="80px" ref="addUserForm">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="user_form.username"></el-input>
                </el-form-item>

                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="user_form.phone"></el-input>
                </el-form-item>

                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="user_form.email"></el-input>
                </el-form-item>

                <el-form-item label="密码" prop="password">
                    <el-input v-model="user_form.password" show-password></el-input>
                </el-form-item>

                <el-form-item label="部门" prop="deptName">
                    <el-row type='flex' :gutter="6">
                      <el-col :span="6">
                          <el-input @focus="inputFocus" v-model="user_form.deptId" readonly ></el-input>
                      </el-col>
                      <el-col :span="14">
                          <el-input @focus="inputFocus" v-model="user_form.deptName" readonly ></el-input>
                      </el-col>
                      <!-- todo:后期优化 -->
                      <el-col :span="4">
                          <el-button type="primary" @click="is_show_dept_tree = false">关闭</el-button>
                      </el-col>
                    </el-row>

                    <SysDeptTree :show-tree='is_show_dept_tree' @childEvent="getTreeNode"></SysDeptTree>
                </el-form-item>

                <el-form-item style="display:flex;justify-content: flex-end;">
                    <el-button type="primary" @click="add_user" :loading="add_user_loading">提交</el-button>
                    <el-button @click="cancel_add_user">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        
        <!-- 编辑表单 -->
        <el-dialog title="编辑用户" :visible.sync="is_show_edit_user_form" width="30%">
                        <el-form :model="user_form" label-width="80px" ref="addUserForm">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="user_form.username"></el-input>
                </el-form-item>

                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="user_form.phone"></el-input>
                </el-form-item>

                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="user_form.email"></el-input>
                </el-form-item>

                <el-form-item label="密码" prop="password">
                    <el-input v-model="user_form.password" show-password></el-input>
                </el-form-item>

                <el-form-item label="部门" prop="deptName">
                    <el-row type='flex' :gutter="6">
                      <el-col :span="6">
                          <el-input @focus="inputFocus" v-model="user_form.deptId" readonly ></el-input>
                      </el-col>
                      <el-col :span="14">
                          <el-input @focus="inputFocus" v-model="user_form.deptName" readonly ></el-input>
                      </el-col>
                      <!-- todo:后期优化 -->
                      <el-col :span="4">
                          <el-button type="primary" @click="is_show_dept_tree = false">关闭</el-button>
                      </el-col>
                    </el-row>

                    <SysDeptTree :show-tree='is_show_dept_tree' @childEvent="getTreeNode"></SysDeptTree>
                </el-form-item>

                <el-form-item style="display:flex;justify-content: flex-end;">
                    <el-button type="primary" @click="edit_user" :loading="edit_user_loading">提交</el-button>
                    <el-button @click="is_show_edit_user_form=false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

    </div>
</template>


<script>
import SysDeptTree from './SysDeptTree'

export default {
    
    data() {
        return {
            // visible:false,
            operation:true,
            table_loading:false,
            add_user_loading:false,
            edit_user_loading:false,
            forbid_user_loading:false,
            show_avatar:'',
            newOpenForbidRow:null,
            is_show_avatar:false,
            is_show_add_user_form:false,
            is_show_dept_tree:false,
            is_show_edit_user_form:false,
            table_data:[],
            query_form:{
                user_name:'',
                start_time:'',
                end_time:'',
                status: '1',
                phone:'',
                deptId:'',
                page:{
                    current_page:1,
                    page_size :10,
                    total_size:1,
                }
            },
            user_form:{
                userId:'',
                username:'',
                password:'123456',
                phone:'',
                email:'',
                avatar:'https://hqjy-coursetest.oss-cn-shenzhen.aliyuncs.com/knowledge_base/image/portrait/20190107/3e2cfd43981f4cb1b7b38a494aea7f36.jpg',
                deptId:'',
                deptName:'',
            }
        }
    },
    methods: {

        openComfirmForbid(row){
            if(this.newOpenForbidRow){
                console.log("存在...")
                this.newOpenForbidRow.visible = false;
            }

            row.visible = true;
            this.newOpenForbidRow = row;
        },


        closeComfirmForbid(row){
            row.visible = false;
            this.newOpenForbidRow = null;
        },

        resertAddUserForm(){
            console.log("重置用户新增框...")
            this.$refs.addUserForm.resetFields();
        },

        /**
         * 使用 $emit 传递给父组件数据
         * 子组件的回调方法，更新部门id 和部门名称
         */
        getTreeNode({id,name}){
            console.log("id:"+ id+" name:"+name)
            this.user_form.deptId = id;
            this.user_form.deptName = name;
        },
        inputFocus(){
            console.log('this.is_show_dept_tree：',this.is_show_dept_tree)
            this.is_show_dept_tree = true;
        },
        queryTable(){
            let _this = this;
            this.table_loading = true;
            let param = {
                userName:this.query_form.user_name,
                deptId:this.query_form.deptId,
                phone:this.query_form.phone,
                startTime:this.query_form.start_time,
                endTime:this.query_form.end_time,
                page:this.query_form.page.current_page,
                limit:this.query_form.page.page_size,
                status:this.query_form.status,
            }

            if(this.query_form.status == 0){
                this.operation = false;
            }else{
                this.operation = true;
            }

            this.$axios.get('/api/v1/users',{
                params : param
            })
            .then(res => {
                console.log("查询列表：",res)
                if(res && res.status == 200 && res.data.code == 200){
                    console.log("Query table data success");
                    _this.table_data = res.data.data.records;
                    _this.query_form.page.total_size = parseInt(res.data.data.total);
                    
                    /**
                     * 插入新字段
                     */
                    _this.table_data.forEach(item =>[
                        _this.$set(item,'visible',false)
                    ])
                    console.log('data',_this.table_data)
                }
                _this.table_loading = false;
            }).catch(res => {
                _this.table_loading = false;
            })
        },

        /**
         * 新增用户
         */
        add_user(){
            this.add_user_loading = true;
            let _this = this;
            let addUserParams = {
                username:this.user_form.username,
                deptId:this.user_form.deptId,
                mobile:this.user_form.phone,
                email:this.user_form.email,
                password:this.user_form.password,
                deptId:this.user_form.deptId,
                avatar:this.user_form.avatar,
            }
            this.$axios.post('/api/v1/users',addUserParams)
            .then(res => {
                console.log("新增用户响应：",res)
                if(res && res.status == 200 && res.data.code == 200){
                    console.log("新增用户成功...");
                    _this.is_show_add_user_form = false;
                    _this.resertAddUserForm();
                    _this.queryTable();
                
                }
                _this.add_user_loading = false;
            })
            .catch(err => {
                console.log("新增用户失败...");
                 _this.add_user_loading = false;
            })
        },

        openEditUserForm(row){
            this.is_show_edit_user_form = true;
            let _this = this;
            this.user_form.userId = row.userId;
            this.$axios('/api/v1/users/'+row.userId)
            .then( res =>{
                if(res && res.status== 200 && res.data.code == 200){
                    let data = res.data.data;
                    if(data){
                        this.user_form.phone = data.mobile;
                        this.user_form.email = data.email;
                        this.user_form.username = data.username;
                        this.user_form.password = data.password;
                        this.user_form.deptId = data.deptId;
                    }
                }
            })
        },

        /**
         * 编辑用户
         */
        edit_user(row){
            this.edit_user_loading = true;
            let _this = this;
            let editUserParams = {
                username:this.user_form.username,
                deptId:this.user_form.deptId,
                mobile:this.user_form.phone,
                email:this.user_form.email,
                password:this.user_form.password,
                deptId:this.user_form.deptId,
                avatar:this.user_form.avatar,
            }
            this.$axios.put('/api/v1/users/'+this.user_form.userId,editUserParams)
            .then(res => {
                console.log("编辑用户响应：",res)
                if(res && res.status == 200 && res.data.code == 200){
                    console.log("编辑用户成功...");
                    _this.is_show_edit_user_form = false;
                    _this.queryTable();
                }
                _this.edit_user_loading = false;
            })
            .catch(err => {
                console.log("编辑用户失败...");
                 _this.edit_user_loading = false;
            })
        },

        handleForbid(row){
            this.forbid_user_loading = true;
            console.log('禁用用户...')
            let _this = this;
            this.closeComfirmForbid(row)
            this.$axios.delete('/api/v1/users/'+row.userId)
            .then(res => {
                _this.forbid_user_loading = false;
                _this.queryTable();
            })
            .catch(err =>{
                _this.forbid_user_loading = false;
            })
        },

        cancel_add_user(){
            this.is_show_add_user_form = false;
            this.resertAddUserForm();
        },

        view_avatar(src){
            console.log('查看头像：src：'+src)
            if(!src){
                this.$message.error('无法查看');
                return;
            }
            this.show_avatar = src;
            this.is_show_avatar = true;
        },
        close_avatar_img(){
            this.is_show_avatar = false;
        },
        handleCurrentChange(val){
             console.log(`当前页: ${val}`);
             this.query_form.page.current_page = val;
             this.queryTable();
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            this.query_form.page.page_size = val;
            this.query_form.page.current_page = 1;
            this.queryTable();
        },
        resetQueryForm(){
            this.query_form.user_name = '';
            this.query_form.start_time = '';
            this.query_form.end_time = '';
            this.query_form.phone ='';
            this.query_form.deptId = '';
            // this.query_form.deptName = '';
            this.query_form.status ='1';
            this.queryTable();
        },
        showAddUserForm(){
            this.is_show_add_user_form = true;
        }
    }, 

    /**
     * 初始化加载
     */
    mounted:function(){
        this.table_loading = true;
        this.queryTable();
    },

    /**
     * 引入组件
     */
    components:{
        SysDeptTree
    }
}
</script>


<style>
    .custom_avatar_view{
        z-index: 2000;
        width: 100%;
        height: 100%;
        background-color: #0000006e;
        position :absolute;
        top: 0px;
        left: 0px;
    }
    .custom_avatar_view_image{
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>
