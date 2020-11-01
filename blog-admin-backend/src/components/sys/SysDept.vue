<template>
    <div style="padding-top:20px;display:flex">
        <div class="custom-frame">
            <el-input placeholder="输入关键字进行过滤" v-model="filterText" style="margin-bottom: 20px">
            </el-input>
            <el-tree :data="data" node-key="id" :expand-on-click-node="false" :render-content="renderContent" 
            :props="defaultProps" :filter-node-method="filterNode" ref="tree" v-loading="treeLoading" highlight-current
            @node-click='handleNodeClick'>
            </el-tree>
        </div>
        <div class="custom-detail" v-if="infoFormVisible" v-loading='infoLoading'>
            <el-card>
                <div slot="header">
                    <span>部门详情</span>
                </div>
                <el-form :model="info">
                    <el-form-item label="部门ID">
                        <span>{{info.deptId}}</span>
                    </el-form-item>
                    <el-form-item label="部门名称">
                        <span>{{info.name}}</span>
                    </el-form-item>
                    <el-form-item label="父级部门ID">
                        <span>{{info.parentId}}</span>
                    </el-form-item>
                    <el-form-item label="排序号">
                        <span>{{info.orderNum}}</span>
                    </el-form-item>
                    <el-form-item label="创建时间">
                        <span>{{info.createTime}}</span>
                    </el-form-item>
                </el-form>
            </el-card>

        </div>

        <el-dialog title="新增部门" :visible.sync="addFormVisible" width="25%" top="16vh" hide-required-asterisk="true"
        @keyup.enter.native="append()">
            <el-form :model="addForm" size="medium" label-width="90px">
              <el-form-item label="上级部门ID" >
                  <el-input :disabled="true" v-model="addForm.pDeptId" placeholder=""></el-input>
              </el-form-item>
              <el-form-item label="部门名" required >
                  <el-input  v-model="addForm.deptName" placeholder=""></el-input>
              </el-form-item>
              <el-form-item label="排序">
                  <el-input v-model="addForm.orderNum" placeholder=""></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addFormVisible=false">取 消</el-button>
                <el-button type="primary" @click="append()">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="编辑部门" :visible.sync="editFormVisible" width="25%" top="16vh" hide-required-asterisk="true"
        @keyup.enter.native="edit()">
            <el-form :model="editForm" size="medium" label-width="90px">
              <el-form-item label="部门名" required >
                  <el-input v-model="editForm.deptName"></el-input>
              </el-form-item>
              <el-form-item label="排序">
                  <el-input v-model="editForm.orderNum"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editFormVisible=false">取 消</el-button>
                <el-button type="primary" @click="edit()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>



<script>
export default {
    data() {
        return {
            info:{},
            addForm:{
                deptName:'',
                orderNum: '1',
                pDeptId:'',
            },
            editForm:{
                id:'',
                deptName:'',
                orderNum:1
            },
            treeLoading:false,
            infoLoading:false,
            filterText:'',
            data:[],
            isNext:false,
            parentNode:{},
            infoFormVisible:false,
            addFormVisible:false,
            editFormVisible:false,
            defaultProps:{
                children:'children',
                label:'name',
                key:'id'
            }
        }
    },
    // 在vue中，使用watch来响应数据的变化, filterText(),表示监听filterText数据
    watch: {
      filterText(val,old) {
        console.log("监听到 filterType 参数变化，新值为："+val+"  旧值为："+ old);
        // filter() 函数会调用 filter-node-method 属性的方法，val参数会传递过去
        this.$refs.tree.filter(val);
      }
    },
    
    methods:{

        queryDeptTree(){
            let _this = this;
            this.$axios.get('/api/v1/dept/tree')
            .then(function(res){
                console.log("response",res);
                if (res && res.status == 200 && res.data.code ==200) {
                    _this.data = res.data.data;
                    console.log("this",this);
                    console.log("_this",_this);
                }else{
                    _this.$message.error("获取数据失败！")
                }
                _this.treeLoading = false;
            })
            .catch(err =>{
                _this.$message.error("获取数据失败！")
                console.log(err);
                _this.treeLoading = false;
            })
             
        },

        handleNodeClick(data){
            this.infoFormVisible = true;
            this.infoLoading = true;
            this.queryDeptInfo(data.id);
        },
        queryDeptInfo(id){
            console.log("查询部门信息，id:"+ id);
            let _this = this;
            this.$axios.get('/api/v1/dept/'+id)
            .then(res =>{
                console.log("查询部门信息，响应：",res);
                if(res && res.status == 200 && res.data.code == 200){
                    _this.info = res.data.data;
                }else{
                    _this.$message.error("查询信息失败 ");
                }
                _this.infoLoading = false;
            })
            .catch(res =>{
                _this.infoLoading = false;
            })
        },

        showAddForm(node,data,next){
            console.log(">>>>> node",node);
            console.log("next:"+next);
            this.isNext = next;
            if(next){
                console.log("新增下级");
                this.addForm.pDeptId = node.key;
            }else{
                console.log("新增同级");
                console.log("node.parent:",node.parent);
                
                this.addForm.pDeptId = node.parent ? node.parent.key : 0;
                this.parentNode = node.parent ? node.parent : node;
            }

            // console.log(">>>>> this.$refs.tree",this.$refs.tree);
            // this.$refs 是啥？
            //  console.log(">>>>> node",this.$refs.tree.getNode(node.key));
            this.addFormVisible=true;
        },

        showEditForm(node,data){
            this.editFormVisible = true;
            console.log("data",data);
            this.editForm.id = data.id;
            this.editForm.deptName = data.name;
            this.editForm.orderNum = 1;
        },

        openDeleteForm(node){
            let _this = this;
            console.log("删除，id: "+node.key);
            this.$confirm('是否删除','警告',{
                confirmButtonText:'确定',
                cancelButtonText:'取消',
                type:'warning'
            }).then(() =>{
                _this.remove(node.key);
            }).catch(() =>{
                console.log("取消删除");
            })
        },

        cleanAddForm(){
            this.addForm.deptName='';
            this.addForm.orderNum='1';
            this.addForm.pDeptId='';
            // this.$refs.addForm.resetFields();
        },
        edit(){
            console.log("编辑");
            this.editFormVisible = false;
            let _this = this;
            let params = {
                "deptName":this.editForm.deptName,
                "orderNum":this.editForm.orderNum
            }
            this.$axios.put('/api/v1/dept/'+this.editForm.id,params).then(res => {
                console.log("edit response",res);
                
                if(res && res.status == 200 && res.data.code == 200){
                    console.log("编辑成功");
                    this.$refs.tree.getNode(this.editForm.id).data.name = res.data.data.name;
                     this.cleanAddForm();
                }else{
                    _this.$message.error('编辑部门失败');
                }
                this.editFormVisible = false;
            })
        },

        append(){
            let _this = this;
            console.log(">>>>> 新增");
            console.log(">>>>> form",this.addForm);
            let addParams = {
                "deptName":this.addForm.deptName,
                "orderNum":this.addForm.orderNum,
                "pid":this.addForm.pDeptId
            }
           
            this.$axios.post('/api/v1/dept',addParams)
            .then(res => {
                console.log("res",res);
                if(res && res.status == 200 && res.data.code ==200){
                    const newChild = {id:res.data.data.deptId,name:res.data.data.name, children: []};
                    console.log("node",this.$refs.tree.getNode(this.addForm.pDeptId));
                    // 插入子类
                    if(this.isNext){
                         _this.$refs.tree.getNode(this.addForm.pDeptId).data.children.push(newChild);
                        // 展开节点
                         _this.$refs.tree.getNode(this.addForm.pDeptId).expanded = true;
                    }else{
                        console.log("nohe11",_this.$refs.tree.getNode(this.addForm.pDeptId));
                        console.log("parentNode :",_this.parentNode);
                        let parent = _this.parentNode;
                        console.log("parent222",parent);
                        if(parent){
                           console.log("222222");
                           // 如果是最高层，data 就是 list
                           if(parent.data instanceof Array){
                               parent.data.push(newChild);
                           }else{
                                parent.data.children.push(newChild);
                           }
                        }
                    }
                }else{
                    _this.$message.error("新增数据失败！"+ res.data.msg)
                }
                this.cleanAddForm();
            })
            .catch(err =>{
                _this.$message.error("新增数据失败！")
                console.log(err);
            })
            
            this.addFormVisible=false;
        },

        remove(id){
            let _this = this;
            console.log(">>>>> 正在删除,id:"+id);
            this.$axios.delete('/api/v1/dept/'+id).then(res => {
                console.log("delete res",res)
                if(res && res.status == 200 && res.data.code == 200){
                    console.log("delete success");
                    console.log("node",_this.$refs.tree.getNode(id));
                    
                    const parent =  _this.$refs.tree.getNode(id).parent;
                    const children = parent.data.children || parent.data;
                    const index = children.findIndex(d => d.id === id);
                    children.splice(index, 1);
                }else{
                    _this.$message.error("删除数据失败!");
                }
                
            })
        },

        filterNode(value, data){
            if(!value){
                return true;
            }
            return data.name.indexOf(value)!== -1
        },
        
        renderContent(h, { node, data, store }){
            return(
                <div class="custom-tree-node">
                    <span>{node.label}</span>
                    <span>
                        <el-button size="mini" type="text" on-click={() => {this.showEditForm(node,data)}}>Edit</el-button>
                        <el-button size="mini" type="text" on-click={() => {this.showAddForm(node,data,false)}}>Insert</el-button>
                        <el-button size="mini" type="text" on-click={() => {this.showAddForm(node,data,true)}}>Append</el-button>
                        <el-button size="mini" type="text" on-click={() => this.openDeleteForm(node)}>Delete</el-button>
                    </span>
                </div>

            )
        }
    },
    mounted:function(){
        this.treeLoading = true;
        this.queryDeptTree();
    }
}
</script>



<style scope>

    

    .custom-frame{
        width: 600px;
        margin-right: 50px;
    }

    .custom-tree-node{
        display: flex;
        align-items: center;
        justify-content: space-between;
        font-size: 15px;
        padding-right: 8px;
        flex:1;
        font-family:"Microsoft YaHei","黑体","宋体",sans-serif;
    }

    .el-tree-node__content{
        height: 38px;
    }

    .el-dialog__body{
        padding: 10px 20px;
    }

    .custom-detail{
        margin-left: 300px;
        width: 600px;
        height: 100%;
        background-color: red
    }

</style>
