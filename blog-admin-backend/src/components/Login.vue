<template>
    <el-form :model="form" ref="form" :rules="rules" v-loading="loading">
        <h3>系统登录</h3>
        <el-form-item class="input" prop="username">
            <el-input placeholder="账号" v-model="form.username"></el-input>
        </el-form-item>

        <el-form-item class="input" prop="password">
            <el-input placeholder="密码" v-model="form.password"></el-input>
        </el-form-item>

        <el-form-item>
            <el-checkbox v-model="form.isRemenber">记住密码</el-checkbox>
        </el-form-item>

        <el-form-item>
            <el-button type="primary" class="login_button" @click="login('form')">登录</el-button>
        </el-form-item>
    </el-form>
</template>


<script>
export default {
    data(){
        return{
            form:{
                username:'admin',
                password:'123',
                isRemenber:false
            },
            loading:false,
            rules:{
                username:[
                    {required:true, message:'请输入用户名',trigger:'blur'}
                ],
                password:[
                    {required:true,message:'请输入密码',trigger:'blur'}
                ]
            }
        }
    },
    methods:{
        login(form){
            var _this = this;
            _this.loading = true;
            // 表单校验
            // 这个 $refs 怎么用？
            this.$refs[form].validate((valid) =>{
                console.log("正在登陆 username："+ this.form.username +" password:"+ this.form.password)
                if(valid){
                    console.log("验证成功");
                    this.postRequest('/login',{
                        username:this.form.username,
                        password:this.form.password
                    }).then(resp => {
                        _this.loading = false;
                        console.log("resp ==> ", resp)
                    })
                }else{
                    console.log("验证失败");
                }
                // this.loading = false;
            })
            
        }
    }
    

}
</script>


<style>
    .el-form{
        margin: 0 auto;
        margin-top: 160px;
        border:1px solid #ffffff;
        box-shadow: 0px 0px 20px 10px #cac6c6;
        width: 450px;
        border-radius: 18px;
    }
    .input{
        margin: 0 auto;
        margin-bottom: 30px;
        width: 380px;
    }

    .el-form h3{
        margin-top: 40px;
        margin-bottom: 35px;
    }

    .login_button{
        width: 380px;;
    }

</style>
