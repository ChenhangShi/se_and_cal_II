<template>
    <div class="room-list">
        <div class="filter">

        </div>
        <div class="list">
            <a-table
                    :columns="columns"
                    :dataSource="rooms"
            >
                <span slot="price" slot-scope="text">
                    <span>￥{{ text }}</span>
                </span>
                <span slot="action" slot-scope="text, record">
                    <a-button type="primary" @click="order(record)">预定</a-button>
                </span>
            </a-table>
        </div>
        <OrderModal></OrderModal>
    </div>
</template>
<script>
    import {mapGetters, mapActions, mapMutations} from 'vuex'
    import {message} from 'ant-design-vue'  // 一般在store的js文件里引入，不优雅，不知道是否可以改进
    import OrderModal from './orderModal'

    const columns = [
        {
            title: '房型',
            dataIndex: 'roomType',
            key: 'roomType',
        },
        {
            title: '床型',
            dataIndex: 'bedType',
            key: 'bedType',
        },
        {
            title: '早餐',
            dataIndex: 'breakfast',
            key: 'breakfast',
        },
        {
            title: '入住人数',
            key: 'peopleNum',
            dataIndex: 'peopleNum',
        },
        {
            title: '房价',
            key: 'price',
            dataIndex: 'price',
            scopedSlots: {customRender: 'price'}
        },
        {
            title: 'Action',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    export default {
        name: 'roomList',
        props: {
            rooms: {
                type: Array
            }
        },
        data() {
            return {
                columns,
            }
        },
        components: {
            OrderModal
        },
        computed: {
            ...mapGetters([
                'orderModalVisible',
                'userInfo' //由前端判断，另一种解决方式是由后端判断，不需要get这个
            ])
        },
        monuted() {

        },
        methods: {
            ...mapMutations([
                'set_orderModalVisible',
                'set_currentOrderRoom'
            ]),
            ...mapActions([]),
            order(record) {
                if (this.userInfo.credit < 0) {
                    message.error('信用不足')
                    return
                }
                this.set_currentOrderRoom(record)
                this.set_orderModalVisible(true)
            }
        }
    }
</script>