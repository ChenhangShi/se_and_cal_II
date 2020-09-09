<template>
    <a-modal
            :visible="addCooperativeCompanyVisible"
            title="添加合作酒店"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <!-- 这里是添加策略模态框区域，请编写表单 -->
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="企业名称" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写企业名"
                        v-decorator="['companyName', { rules: [{ required: true, message: '请填写企业名' }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    /**
     * 添加酒店合作企业的对话框
     * */
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        name: 'addCooperativeCompanyModal',
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
        computed: {
            ...mapGetters([
                'addCooperativeCompanyVisible',
            ]),
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, {name: 'addCooperativeCompanyModal'});
        },
        created() {
        },
        mounted() {
        },
        methods: {
            ...mapMutations([
                "set_addCooperativeCompanyVisible",
            ]),
            ...mapActions([
                "addCooperativeCompany",
            ]),
            cancel() {
                this.set_addCooperativeCompanyVisible(false)
            },

            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            companyName: this.form.getFieldValue('companyName'),
                        }
                        this.addCooperativeCompany(data)
                        this.form.resetFields()
                    }
                });


            },
        }
    }
</script>