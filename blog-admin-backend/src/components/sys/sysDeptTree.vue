<template>
    <div v-if="showTree" class="custom_tree">
        <el-card>
            <el-tree ref='tree' :data="data" show-checkbox node-key="id" v-loading="treeLoading" 
            :props="defaultProps" @check-change='handleCheckChange' default-expand-all check-strictly></el-tree>
            <!-- <div style="display: flex;justify-content: flex-end;">
                <el-button size="mini" type="primary" @click="isShowTree = false">关闭</el-button>
            </div> -->
        </el-card>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                treeLoading:false,
                data:[],
                pickDept:{
                    id:'',
                    name:'',
                },
                defaultProps:{
                    children:'children',
                    label:'name',
                    key:'id'
                }
            }
        },
        props:{
            showTree:Boolean,
        },
        methods: {
            queryDeptTree(){
                let _this = this;
                this.$axios.get('/api/v1/dept/tree')
                .then(function(res){
                    console.log("response",res);
                    if (res && res.status == 200 && res.data.code ==200) {
                        _this.data = res.data.data;
                    }else{
                        _this.$message.error("获取数据失败！")
                    }
                    _this.treeLoading = false;
                })
                .catch(err =>{
                    _this.$message.error("获取数据失败！")
                    _this.treeLoading = false;
                })
                
            },

            // handleCheck(data,node){
            //     console.log(data,node);
            //     this.pickDept.id = data.id;
            //     this.pickDept.name = data.name;
            // },

            /**
             * 实现 tree 的单选功能
             */
            handleCheckChange(data,isCheck,self){
                console.log(data,isCheck,self);
                if(isCheck){
                    // 点击未选择的
                    this.pickDept.id = data.id;
                    this.pickDept.name = data.name;
                    this.$refs.tree.setCheckedKeys([data.id])

                    // 向父组件传递数据
                    this.$emit('childEvent',  {id:  data.id ,name: data.name});
                }else{
                    // 点击已选择的
                    if(data.id == this.pickDept.id){
                        this.pickDept.id = '';
                        this.pickDept.name = '';
                        this.$refs.tree.setCheckedKeys([])
                        // 向父组件传递数据
                        this.$emit('childEvent',  {id:  '' ,name: ''});
                    }
                }
            }
        },
        mounted:function(){
            this.treeLoading = true;
            this.queryDeptTree();
        },

    }

</script>


<style>
    .custom_tree{
        position:absolute;
        z-index: 100;
        width: 100%;
        max-height: 300px;
        overflow: auto;
    }

</style>
