<template>
    <div>
        <a-modal
                :visible="selectRestoreMethodModalVisible"
                title="选择要回复的信用值"
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
                <a-form-item label="选择信用值恢复方式">
                    <a-select
                            v-decorator="[
                            'restoreType',
                            { rules: [{ required: true, message: '请选择恢复方式' }] }]"
                    >
                        <a-select-option value="full">
                            恢复全部信用
                        </a-select-option>
                        <a-select-option value="half">
                            恢复一半信用
                        </a-select-option>

                    </a-select>
                </a-form-item>
            </a-form>

        </a-modal>
    </div>
</template>

<script>
    /**
     * 选择撤销异常订单的时候恢复全部信用还是一半信用
     * */
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    const moment = require('moment')
    export default {
        name: "selectRestoreMethodModal",
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
                'selectRestoreMethodModalVisible',
                'currentOrder',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'selectRestoreMethodModal'});
        },
        methods: {
            ...mapMutations([
                "set_selectRestoreMethodModalVisible",
            ]),
            ...mapActions([
                "annualUnusualOrder",
            ]),
            handelCancel() {
                this.set_selectRestoreMethodModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    let data = {
                        orderId: this.currentOrder.id,
                        restoreMethod: String(this.form.getFieldValue('restoreType')),
                    }
                    this.annualUnusualOrder(data)
                    this.set_selectRestoreMethodModalVisible(false)
                    this.form.resetFields()

                })
            },
        },


    }
</script>

<style scoped>

</style>