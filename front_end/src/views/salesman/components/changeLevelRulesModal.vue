<template>
    <div>
        <a-modal
                :visible="changeLevelRulesModalVisible"
                title="更改等级规则"
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
            <a-form :form="form">
                <a-form-item label="信用值">
                    <a-input

                            placeholder="请输入达到该等级所需信用值"
                            v-decorator="[
                            'credit',
                            {
                                initialValue: levelRecord.credit
                            },
                            { rules: [{ required: true, message: '请输入折扣数' },
                            {validator: (_, value, callback) => {//检验信用值的合理性，即信用值按照等级从小到大排列
                                let pos = this.levelRecord.level - 1
                                //改到这里了
                            }
                            }
                             ]
                            },
                            ]"
                    >
                    </a-input>
                </a-form-item>

                <a-form-item label="折扣数">
                    <a-input
                            placeholder="请输入该等级的折扣数，例如0.8表示八折"
                            v-decorator="[
                            'discount',
                            {
                            initialValue: levelRecord.discount,
                            rules: [{ required: true, message: '请输入折扣数' },
                            {validator: (_, value, callback) => { //检验输入，输入为小数
                                if(value.length == 0){
                                    return callback()
                                }
                                let reg =  /^[0-9]+([.]{1}[0-9]+){0,1}$/
                                if(!reg.test(value) || Number(value)>1){
                                    return callback('非法输入！');
                                }
                                return callback()
                            }}
                            ]}]"
                    >

                    </a-input>
                </a-form-item>

            </a-form>
        </a-modal>
    </div>

</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    const moment = require('moment')
    export default {
        name: "changeLevelRulesModal",
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
            }
        },
        props: {
            levelRecord: {},
            memberLevelInfo: {},
        },
        computed: {
            ...mapGetters([
                'changeLevelRulesModalVisible',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'selectRestoreMethodModal'});
        },
        methods: {
            ...mapMutations([
                "set_changeLevelRulesModalVisible",
            ]),
            ...mapActions([
                "updateMemberLevelInfo",
                "getMemberLevelInfo",
            ]),
            handelCancel() {
                this.form.resetFields();
                this.set_changeLevelRulesModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        let data = {
                            level: Number(this.levelRecord.level),
                            credit: Number(this.form.getFieldValue('credit')),
                            discount: Number(this.form.getFieldValue('discount')),
                        }
                        this.updateMemberLevelInfo(data)
                        this.getMemberLevelInfo()
                        this.form.resetFields()
                        this.set_changeLevelRulesModalVisible(false)
                    }
                })
            }
        },
    }
</script>

<style scoped>

</style>