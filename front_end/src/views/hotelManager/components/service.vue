<template>
    <div>
        <a-modal
                :visible="serviceVisible"
                title="酒店服务"
                width="900px"
                :footer="null"
                @cancel="cancel"
        >
            <div style="width: 100%; text-align: right; margin:20px 0">
                <a-button type="primary" @click="addService">
                    <a-icon type="plus"/>
                    添加服务
                </a-button>
            </div>
            <a-table
                    :columns="columns"
                    :dataSource="targetHotel.services"
                    bordered
            >
                <span slot="action" slot-scope="record">
                    <a-popconfirm
                            title="确定想删除该服务吗？"
                            @confirm="deleteService(record)"
                            okText="确定"
                            cancelText="取消"
                    >
                        <a-button type="danger" size="small">删除服务</a-button>
                    </a-popconfirm>
                </span>
            </a-table>
        </a-modal>
        <AddService></AddService>
    </div>
</template>
<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import AddCoupon from './addCouponModel'
    import AddService from "./addService";

    const columns = [
        // 这里定义列表头
        {
            title: '服务',
            dataIndex: 'serviceType',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    export default {
        name: 'service',
        data() {
            return {
                columns,
                missingServices: [],
            }
        },
        components: {
            AddService

        },
        computed: {
            ...mapGetters([
                'serviceVisible',
                'targetHotel',
                'addServiceVisible',
            ])
        },
        methods: {
            ...mapMutations([
                "set_serviceVisible",
                "set_addServiceVisible",
            ]),
            ...mapActions([
                "deleteHotelService",
            ]),
            cancel() {
                this.set_serviceVisible(false)
            },
            addService() {
                this.set_addServiceVisible(true),
                this.set_serviceVisible(false)
            },
            deleteService(record){
                const data = {
                    hotelId: this.targetHotel.id,
                    serviceType: record.serviceType,
                }
                this.deleteHotelService(data)
            }
        },
    }
</script>
<style scoped>

</style>