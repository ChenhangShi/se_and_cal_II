<template>
    <a-modal
            :visible="addServiceVisible"
            title="添加服务"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <!-- 这里是添加策略模态框区域，请编写表单 -->
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="酒店服务" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                        'serviceType',
                        { rules: [{ required: true, message: '请选择要添加的服务' }] }]"
                >
                    <a-select-option :value="item" v-for="item of missingServices" :key="item">
                        {{item}}
                    </a-select-option>
            </a-select>
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    /**
     * 添加酒店服务对话框
     * */
    import {mapGetters, mapMutations, mapActions} from 'vuex'

    export default {
        name: 'addService',
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
                'addServiceVisible',
                'allServicesList',
                'targetHotel',
            ]),
            /**
             * 根据现有服务和所有服务，求出还未添加的服务，用于渲染下拉框
             * */
            missingServices: function(){
                let curServices=[]
                for(let i in this.targetHotel.services){
                    curServices.push(this.targetHotel.services[i].serviceType)
                }
                let missingServices=[]
                for(let i in this.allServicesList){
                    if(curServices.indexOf(this.allServicesList[i])==-1)
                        missingServices.push(this.allServicesList[i])
                }
                return missingServices
            },
        },


        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, {name: 'addService'});
        },
        created() {
        },
        async mounted() {
            await this.getAllServices()
        },
        methods: {
            ...mapMutations([
                "set_addServiceVisible",
            ]),
            ...mapActions([
                "addHotelService",
                "getAllServices",
            ]),
            cancel() {
                this.set_addServiceVisible(false)
            },

            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data={
                            hotelId: this.targetHotel.id,
                            serviceType: this.form.getFieldValue('serviceType')
                        }
                        this.addHotelService(data)
                        this.form.resetFields()
                    }
                });
            },
        }
    }
</script>