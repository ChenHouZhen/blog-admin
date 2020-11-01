<template>
    <div>
        <el-button type='primary' @click="get">获取表格</el-button>
        <el-button type='primary' @click="refresh">刷新表格</el-button>
        <button id='self' class="button" type="button"><span>自定义按钮</span></button>
        <div>
            <el-table :data="menuData" max-height="250" style="width:100%" v-loading="tableLoading" row-key="path"  :tree-props="{children: 'children'}">>
                <!-- <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form>
                            <el-table-column label="姓名" prop="">
                                <span>{{props.row.children.name}}</span>
                            </el-table-column>
                        </el-form>
                    </template>
                </el-table-column> -->
                <el-table-column prop="id" label="ID" width="180px"></el-table-column>
                <el-table-column prop="name" label="姓名" width="180px" >
                    <template slot-scope="scope">
                        <el-tag size="medium">{{ scope.row.name }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="path" label="路径" width="180px"></el-table-column>
                <el-table-column prop="iconCls" label="图标" width="180px"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination background layout="prev, pager, next" :total="tableTotal" :current-page.sync="currentPage">
            </el-pagination>
        </div>

        <ul class="nav" @click="selectNav">
            <li v-for="(item, index) in navList" :key="index" :data-index="index">{{item}}</li>
        </ul>
    </div>
</template>



<script>

export default {
    name: 'Button',
    data() {
        return {
            menuData:[],
            msg:"按钮",
            navList:[1,2,3,4,5],
            tableLoading:false,
            tableTotal: -1,
            currentPage:1,
        }
    },
    methods: {
        get(){
            let _this = this;
            _this.tableLoading = true;
            this.$axios.get("/api/config/sysmenu").then(function(res) {
                console.log("res",res);
                if (res && res.status == 200) {
                    _this.tableLoading = false;
                    _this.menuData = res.data;                    
                }
            })
        },
        refresh(){
            this.menuData=[];
        },
        selectNav(e){
            console.log("e",e);
            let dom  = e.target;
            console.log(dom);
            let index = dom.getAttribute('data-index');
            console.log("index:"+ index);
            this.$message("index: "+index +"  value: " );
        },
        handleEdit(index, row){
            console.log("正在编辑");
            console.log("index: "+index + " row: ", row);
            
        },
        handleDelete(index, row){
            console.log("正在删除");
            console.log("index: "+index + " row: ", row);
        }
    },
}
</script>


<style>

    .button{
        color: #fff;
        font-size: 14px;
        padding: 12px;
        border-radius: 4px;
        border:1px solid #409EFF;
        background-color: #409EFF;
        margin-left: 10px;
        cursor: pointer;
        transition: 0.5s;
        line-height: 1;
    }

    .button:hover{
        background-color: rgb(117, 181, 236);
    }

    .nav{   
        display: flex;
        justify-content: flex-start;
    }

    .nav > li {
        line-height: 30px;
        box-shadow: 6px 5px 15px 2px rgba(0, 0, 0, 0.349);
        border-radius: 30px;
        width: 30px;
        height: 30px;
        list-style: none;
        margin: 10px;
        cursor: pointer;
        overflow: hidden;
        text-overflow: ellipsis;
    }
</style>


