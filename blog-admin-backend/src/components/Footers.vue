<template>
    <el-footer style="height:45px">
        <span><strong>Copyright</strong>  @2019-2020 <strong> by JK</strong></span>
    </el-footer>
</template>


<script>
export default {
    name:'footers'
}
</script>

<style>
    .el-footer{
        line-height: 45px;
        position: fixed;
        left: 0px;
        bottom: 0px;
        width: 100%;
        background-color: #eee;
        z-index: 9999;
        font-size: 12px;
    }

    .el-footer span{
        position: absolute;
        right: 109px;
    }
    
</style>
