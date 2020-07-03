<template>
    <div>
        <a-modal
                :visible="addCreditModalVisible"
                title="填写要充值的信用点数"
                @cancel="handelCancel"
        >
            <template slot="footer">
                <a-button key="back" @click="handelCancel">
                    返回
                </a-button>
                <a-button key="submit" type="primary" @click="handleSubmit">
                    确定
                </a-button>
            </template>
            <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
                <a-form-item label="充值额度" v-bind="formItemLayout">
                    <a-input
                            placeholder="到账信用值=充值额度*100"
                            v-decorator="['amount', { rules: [{ required: true, message: '请填写充值额度' },
                            {validator: (_, value, callback) => { //检验输入，输入为正整数和小数
                            if(value.length == 0){
                                return callback()
                            }
                            let reg =  /^[0-9]+([.]{1}[0-9]+){0,1}$/
                            if(!reg.test(value)){
                                return callback('非法输入！');
                            }
                            return callback()
                            }}
                            ] }]"
                    />
                </a-form-item>
            </a-form>
        </a-modal>
    </div>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    const moment = require('moment')
    export default {
        name: "addCreditModal",
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
        props: ['curUserId', 'memberLevelInfo'],
        computed: {
            ...mapGetters([
                'addCreditModalVisible',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'addCreditModal'});
        },
        methods: {
            ...mapMutations([
                "set_addCreditModalVisible",
            ]),
            ...mapActions([
                "addCredit",
            ]),
            handelCancel() {
                this.set_addCreditModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    let data = { //虽然数据不多，但还是习惯打包传递
                        userId: Number(this.curUserId),
                        amount: Number(this.form.getFieldValue('amount')),
                    }
                    this.addCredit(data)
                    this.set_addCreditModalVisible(false)
                    this.form.resetFields()

                })
            }
        }
    }
</script>

<style scoped>

</style>