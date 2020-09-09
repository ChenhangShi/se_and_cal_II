<template>
    <div>
        <a-modal
                :visible="roomListModalVisible"
                title="房间信息"
                @cancel="handleCancel"
        >
            <template slot="footer">
                <a-button key="back" @click="handleCancel">
                    返回
                </a-button>
            </template>
            <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
        </a-modal>
    </div>
</template>

<script>
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import RoomList from "./roomList";

    const moment = require('moment')
    export default {
        name: "roomListModal",
        components: {
            RoomList,
        },
        computed: {
            ...mapGetters([
                "roomListModalVisible",
                "currentHotelInfo",
            ])
        },
        methods: {
            ...mapMutations([
                "set_roomListModalVisible",
            ]),
            ...mapMutations([]),
            handleCancel() {
                this.set_roomListModalVisible(false)
            }
        }
    }
</script>

<style scoped>

</style>