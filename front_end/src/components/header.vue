<template>
    <div class="header">
        <div class="label">
            <img src="@/assets/logo.svg" class="logo" alt="logo" @click="jumpToHome">
            <span class="title">NJUSE 酒店管理系统</span>

        </div>
        <a-menu v-model="current" mode="horizontal" theme="light">
            <a-menu-item key="1" @click="selectMenu">
                <router-link to="/hotel/hotelList">
                    <a-icon type="home"/>
                    首页
                </router-link>
            </a-menu-item>
            <a-menu-item key="2" @click="jumpToUserInfo" v-if="userInfo.userType=='Client'">
                <a-icon type="user"/>
                个人中心
            </a-menu-item>
            <a-menu-item key="3" @click="jumpToHotelManagerInfo" v-if="userInfo.userType=='HotelManager'">
                <router-link :to="{ name: 'manageHotel'}">
                    <a-icon type="switcher"/>
                    酒店管理
                </router-link>
            </a-menu-item>
            <a-menu-item key="4" @click="selectMenu" v-if="userInfo.userType=='Admin'">
                <router-link :to="{ name: 'manageUser'}">
                    <a-icon type="user"/>
                    账户管理
                </router-link>
            </a-menu-item>
            <a-menu-item key="5" @click="selectMenu" v-if="userInfo.userType=='Salesman'">
                <router-link :to="{ name: 'salesStrategy'}">
                    <a-icon type="user"/>
                    网站营销
                </router-link>
            </a-menu-item>
        </a-menu>
        <div class="logout">
            <a-dropdown placement="bottomCenter">
                <div class="user">
                    <a-avatar src="./defaultAvatar.png"></a-avatar>
                    <span style="font-size: 14px">{{ userInfo.userName }}</span>
                    <a-icon style="margin-left: 3px; font-size: 16px" type="down"></a-icon>
                </div>
                <a-menu slot="overlay">
                    <a-menu-item @click="jumpToHome()">
                        <a-icon type="home"></a-icon>
                        首页
                    </a-menu-item>
                    <a-menu-item @click="jumpToUserInfo()">
                        <a-icon type="profile"></a-icon>
                        我的信息
                    </a-menu-item>
                    <a-menu-item @click="quit()">
                        <a-icon type="poweroff"></a-icon>
                        退出登录
                    </a-menu-item>
                </a-menu>
            </a-dropdown>
        </div>

    </div>

</template>
<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex'

    export default {
        name: '',
        data() {
            return {
                current: ['1']
            }
        },
        computed: {
            ...mapGetters([
                'userId',
                'userInfo',
            ])
        },
        created() {
            if (this.userInfo.userId == null) {
                //console.log('之前：')
                //console.log(this.userInfo)
                if (sessionStorage.getItem("userId")) {
                    this.set_userId(Number(sessionStorage.getItem("userId")))
                    //console.log("从缓存中读取的userId：")
                    //console.log(this.userId)
                    this.getUserInfo()
                }

            }

        },


        mounted() {
            if (this.$route.name == 'hotelList' || this.$route.name == 'hotelDetail') {
                this.current = ['1']
            } else if (this.$route.name == 'userInfo') {
                this.current = ['2']
            } else if (this.$route.name == 'manageHotel') {
                this.current = ['3']
            } else { //这里指的是manageUser，但是不知道current的用处
                this.current = ['4']
            }
        },
        methods: {
            ...mapMutations([
                "set_userId",

            ]),
            ...mapActions([
                "getUserInfo",
                'logout',
            ]),
            selectMenu(v) {
            },
            async quit() {
                await this.$store.dispatch('logout')
                this.$router.push(`/login?redirect=${this.$route.fullPath}`)
            },
            jumpToUserInfo() { //仿照这个方法，可以把不同酒店的hotelManager区别开
                this.$router.push({name: 'userInfo', params: {userId: this.userId}})
            },
            jumpToHome() {

            },
            //迭代3.3 为了区分不同酒店的不同hotelManager，所以hotelManager的url也带参数
            jumpToHotelManagerInfo() {
                this.$router.push({name: 'manageHotel', params: {userId: this.userId}})
            }
        }
    }
</script>
<style scoped lang="less">
    .header {
        display: flex;
        line-height: 44px;
        height: 44px;
        align-items: center;
        justify-content: space-between;
        min-width: 800px;

        .label {
            height: 44px;
            line-height: 44px;
            vertical-align: middle;
            min-width: 400px;

            .logo {
                height: 44px;
                vertical-align: top;
                margin-right: 16px;
                border-style: none;
                cursor: pointer;
            }

            .title {
                font-size: 33px;
                color: rgba(0, 0, 0, .85);
                font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
                font-weight: 600;
                position: relative;
                top: 2px;
            }

            .search {
                width: 300px;
                margin-left: 30px
            }
        }

        .logout {
            margin-right: 40px;

            .user {
                cursor: pointer;
                display: flex;
                align-items: center;

                span {
                    margin-left: 5px
                }
            }
        }

    }
</style>
<style lang="less">
    .header {
        .ant-menu {
            background: none
        }
    }
</style>