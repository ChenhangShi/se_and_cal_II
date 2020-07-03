<template>
    <div>
        <a-modal
                :visible="registerMemberModalVisible"
                title="注册会员"
                @cancel="handleCancel"
        >
            <template slot="footer">
                <a-button key="back" @click="handleCancel">
                    返回
                </a-button>
                <a-button key="submit" type="primary" @click="handleSubmit">
                    注册
                </a-button>
            </template>
            <div>
                <a-form :form="form" v-bind="formItemLayout">
                    <a-form-item label="选择会员类型" v-bind="formItemLayout">
                        <a-select
                                v-decorator="[
                        'type',
                        { rules: [{ required: true, message: '请选择会员类型' }] }]"
                                @change="changeMemberType"
                        >
                            <a-select-option value="1">
                                普通会员
                            </a-select-option>
                            <a-select-option value="2">
                                企业会员
                            </a-select-option>

                        </a-select>
                    </a-form-item>
                    <a-form-item v-if="memberType==1" label="请填写生日" v-bind="formItemLayout">
                        <a-date-picker
                                format="YYYY-MM-DD"

                                v-decorator="[
                        'birthday',
                        {
                            rules: [{ required: true, message: '请填写出生日期' }]
                        }
                         ]"
                        >
                        </a-date-picker>
                    </a-form-item>
                    <a-form-item v-if="memberType==2" label="请填写企业名称" v-bind="formItemLayout">
                        <a-input
                                placeholder="请填写企业名称"
                                v-decorator="['companyName', { rules: [{ required:  true, message: '请填写企业名称' }] }]"
                        />
                    </a-form-item>

                </a-form>
            </div>
        </a-modal>
    </div>
</template>

<script>
    /**
     * 用户注册会员对话框
     * */
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    const moment = require('moment')
    export default {
        name: "registerMemberModal",
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: {span: 12},
                        sm: {span: 6},
                    },
                    wrapperCol: {
                        xs: {span: 24},
                        sm: {span: 16},
                    },
                },
                memberType: Number
            }
        },
        computed: {
            ...mapGetters([
                'userInfo',
                'registerMemberModalVisible',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'registerMemberModal'});
        },
        methods: {
            ...mapMutations([
                "set_registerMemberModalVisible",
            ]),
            ...mapActions([
                "getUserInfo",
                "registerMember",
            ]),
            /**
             * 根据下拉框的会员类型选择，动态显示表单内容
             * */
            changeMemberType(v) {
                this.memberType = Number(v)
            },
            handleCancel() {
                this.set_registerMemberModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        let data = null
                        if (this.memberType == 1) {
                            data = {
                                userId: Number(this.userInfo.id),
                                type: Number(this.form.getFieldValue('type')),
                                level: 1,
                                birthday: String(this.form.getFieldValue('birthday').format('YYYY-MM-DD')),
                                companyName: null
                            }
                        } else if (this.memberType == 2) {
                            data = {
                                userId: Number(this.userInfo.id),
                                type: Number(this.form.getFieldValue('type')),
                                level: 1,
                                birthday: null,
                                companyName: String(this.form.getFieldValue('companyName')),
                            }
                        }
                        //console.log("注册会员表单数据：")
                        //console.log(data)
                        this.registerMember(data)
                        this.form.resetFields()
                        this.set_registerMemberModalVisible(false)
                    }
                })
            }
        },

    }
</script>

<style scoped>

</style>