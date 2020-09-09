<template>
    <div>
        <a-modal
                :visible="addCommentModalVisible"
                title="添加评论"
                @cancel="handleCancel"
        >
            <template slot="footer">
                <a-button key="back" @click="handleCancel">
                    取消
                </a-button>
                <a-button key="submit" type="primary" @click="handleSubmit">
                    发表评论
                </a-button>
            </template>
            <a-form :form="form" v-bind="formItemLayout">
                <a-form-item label="评分:" v-bind="formItemLayout">
                    <a-rate
                            v-decorator="[
                    'rate',
                    { rules: [{ required: true, message: '请打分' }] }]"
                            :initial-value="5" allow-half/>
                </a-form-item>
                <a-form-item label="评论:" v-bind="formItemLayout">
                    <a-textarea
                            v-decorator="[
                    'commentContent',
                    { rules: [{ required: true, message: '请填写评论' }] }]"
                            placeholder="请输入评论"
                            :auto-size="{ minRows: 3, maxRows: 10 }"
                    />
                </a-form-item>
            </a-form>

        </a-modal>
    </div>

</template>

<script>
    /**
     * 用户评分、评论的对话框
     * */
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    const moment = require('moment')
    export default {
        name: "addCommentModal",
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
                'currentOrder',
                'userInfo',
                'addCommentModalVisible',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, {name: 'commentModal'});
        },
        methods: {
            ...mapMutations([
                'set_addCommentModalVisible',
            ]),

            ...mapActions([
                "postComment",
            ]),
            handleCancel() {
                this.set_addCommentModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            hotelId: this.currentOrder.hotelId,
                            hotelName: this.currentOrder.hotelName,
                            userId: this.userInfo.id,
                            userName: this.userInfo.userName,
                            rate: Number(this.form.getFieldValue('rate')),
                            comment: String(this.form.getFieldValue('commentContent')),
                        }
                        this.postComment(data)
                        //重置表单，防止重复操作
                        this.form.resetFields()
                    }
                })

            }
        }
    }
</script>

<style scoped>

</style>