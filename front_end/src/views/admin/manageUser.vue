<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addHotel">
                        <a-icon type="plus"/>
                        添加酒店
                    </a-button>
                </div>
                <a-table
                        :columns="columns2"
                        :dataSource="hotelList"
                        bordered
                >
                    <div
                            slot="filterDropdown"
                            slot-scope="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
                            style="padding: 8px"
                    >
                        <a-input
                                v-ant-ref="c => (searchInput = c)"
                                :placeholder="`Search ${column.dataIndex}`"
                                :value="selectedKeys[0]"
                                style="width: 188px; margin-bottom: 8px; display: block;"
                                @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                                @pressEnter="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                        />
                        <a-button
                                type="primary"
                                icon="search"
                                size="small"
                                style="width: 90px; margin-right: 8px"
                                @click="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                        >
                            Search
                        </a-button>
                        <a-button size="small" style="width: 90px" @click="() => handleReset(clearFilters)">
                            Reset
                        </a-button>
                    </div>
                    <a-icon
                            slot="filterIcon"
                            slot-scope="filtered"
                            type="search"
                            :style="{ color: filtered ? '#108ee9' : undefined }"
                    />
                    <template slot="customRender" slot-scope="text, record, index, column">
                        <span v-if="searchText && searchedColumn === column.dataIndex">
                            <template
                                    v-for="(fragment, i) in text
                                .toString()
                                .split(new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i'))"
                            >
                                <mark
                                        v-if="fragment.toLowerCase() === searchText.toLowerCase()"
                                        :key="i"
                                        class="highlight"
                                >
                                    {{ fragment }}
                                </mark>

                                <template v-else>{{ fragment }}</template>
                            </template>
                        </span>
                        <template v-else>
                            {{ text }}
                        </template>
                    </template>


                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" v-if="record.managerId==null"
                                  @click="addManagerOfThisHotel(record)">添加工作人员</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="确定想删除该酒店吗？"
                                @confirm="deleteHotel(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除酒店</a-button>
                        </a-popconfirm>

                    </span>
                </a-table>
            </a-tab-pane>

            <a-tab-pane tab="普通用户管理" key="2">
                <a-table
                        :columns="columns1"
                        :dataSource="normalUserList"
                        bordered
                >
                    <div
                            slot="filterDropdown"
                            slot-scope="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
                            style="padding: 8px"
                    >
                        <a-input
                                v-ant-ref="c => (searchInput = c)"
                                :placeholder="`Search ${column.dataIndex}`"
                                :value="selectedKeys[0]"
                                style="width: 188px; margin-bottom: 8px; display: block;"
                                @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                                @pressEnter="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                        />
                        <a-button
                                type="primary"
                                icon="search"
                                size="small"
                                style="width: 90px; margin-right: 8px"
                                @click="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                        >
                            Search
                        </a-button>
                        <a-button size="small" style="width: 90px" @click="() => handleReset(clearFilters)">
                            Reset
                        </a-button>
                    </div>
                    <a-icon
                            slot="filterIcon"
                            slot-scope="filtered"
                            type="search"
                            :style="{ color: filtered ? '#108ee9' : undefined }"
                    />
                    <template slot="customRender" slot-scope="text, record, index, column">
                        <span v-if="searchText && searchedColumn === column.dataIndex">
                            <template
                                    v-for="(fragment, i) in text
                                .toString()
                                .split(new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i'))"
                            >
                                <mark
                                        v-if="fragment.toLowerCase() === searchText.toLowerCase()"
                                        :key="i"
                                        class="highlight"
                                >
                                    {{ fragment }}
                                </mark>

                                <template v-else>{{ fragment }}</template>
                            </template>
                        </span>
                        <template v-else>
                            {{ text }}
                        </template>
                    </template>


                    <span slot="action" slot-scope="text, record">
                        <a-popconfirm
                                title="确定想删除该用户吗？"
                                @confirm="deleteUser(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger">删除用户</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>

            <a-tab-pane tab="工作人员管理" key="3">
                <!--div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addManager"><a-icon type="plus" />添加用户</a-button>
                </div-->
                <a-table
                        :columns="columns1"
                        :dataSource="managerList"
                        bordered
                >
                    <div
                            slot="filterDropdown"
                            slot-scope="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
                            style="padding: 8px"
                    >
                        <a-input
                                v-ant-ref="c => (searchInput = c)"
                                :placeholder="`Search ${column.dataIndex}`"
                                :value="selectedKeys[0]"
                                style="width: 188px; margin-bottom: 8px; display: block;"
                                @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                                @pressEnter="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                        />
                        <a-button
                                type="primary"
                                icon="search"
                                size="small"
                                style="width: 90px; margin-right: 8px"
                                @click="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                        >
                            Search
                        </a-button>
                        <a-button size="small" style="width: 90px" @click="() => handleReset(clearFilters)">
                            Reset
                        </a-button>
                    </div>
                    <a-icon
                            slot="filterIcon"
                            slot-scope="filtered"
                            type="search"
                            :style="{ color: filtered ? '#108ee9' : undefined }"
                    />
                    <template slot="customRender" slot-scope="text, record, index, column">
                        <span v-if="searchText && searchedColumn === column.dataIndex">
                            <template
                                    v-for="(fragment, i) in text
                                .toString()
                                .split(new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i'))"
                            >
                                <mark
                                        v-if="fragment.toLowerCase() === searchText.toLowerCase()"
                                        :key="i"
                                        class="highlight"
                                >
                                    {{ fragment }}
                                </mark>

                                <template v-else>{{ fragment }}</template>
                            </template>
                        </span>
                        <template v-else>
                            {{ text }}
                        </template>
                    </template>


                    <span slot="action" slot-scope="text, record">
                        <a-popconfirm
                                title="确定想删除该用户吗？"
                                @confirm="deleteHotelManager(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger">删除用户</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="营销人员管理" key="4">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addSalesman">
                        <a-icon type="plus"/>
                        添加用户
                    </a-button>
                </div>
                <a-table
                        :columns="columns1"
                        :dataSource="salesmanList"
                        bordered
                >
                    <div
                            slot="filterDropdown"
                            slot-scope="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
                            style="padding: 8px"
                    >
                        <a-input
                                v-ant-ref="c => (searchInput = c)"
                                :placeholder="`Search ${column.dataIndex}`"
                                :value="selectedKeys[0]"
                                style="width: 188px; margin-bottom: 8px; display: block;"
                                @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                                @pressEnter="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                        />
                        <a-button
                                type="primary"
                                icon="search"
                                size="small"
                                style="width: 90px; margin-right: 8px"
                                @click="() => handleSearch(selectedKeys, confirm, column.dataIndex)"
                        >
                            Search
                        </a-button>
                        <a-button size="small" style="width: 90px" @click="() => handleReset(clearFilters)">
                            Reset
                        </a-button>
                    </div>
                    <a-icon
                            slot="filterIcon"
                            slot-scope="filtered"
                            type="search"
                            :style="{ color: filtered ? '#108ee9' : undefined }"
                    />
                    <template slot="customRender" slot-scope="text, record, index, column">
                        <span v-if="searchText && searchedColumn === column.dataIndex">
                            <template
                                    v-for="(fragment, i) in text
                                .toString()
                                .split(new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i'))"
                            >
                                <mark
                                        v-if="fragment.toLowerCase() === searchText.toLowerCase()"
                                        :key="i"
                                        class="highlight"
                                >
                                    {{ fragment }}
                                </mark>

                                <template v-else>{{ fragment }}</template>
                            </template>
                        </span>
                        <template v-else>
                            {{ text }}
                        </template>
                    </template>

                    <span slot="action" slot-scope="text, record">
                        <a-popconfirm
                                title="确定想删除该用户吗？"
                                @confirm="deleteSalesman(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger">删除用户</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddManagerModal></AddManagerModal>
        <AddHotelModal></AddHotelModal>
    </div>
</template>
<script>
    /**
     * 管理员的"用户管理"界面
     * */
    import {mapGetters, mapMutations, mapActions} from 'vuex'
    import AddManagerModal from './components/addManagerModal'
    import AddHotelModal from './components/addHotelModal'
    import hotelList from "../hotel/hotelList";

    const columns1 = [
        {
            title: '用户ID',
            dataIndex: 'id',
            key: 'id',
            // 以下是在table中搜索相关的部分
            scopedSlots: {
                filterDropdown: 'filterDropdown',
                filterIcon: 'filterIcon',
                customRender: 'customRender',
            },
            onFilter: (value, record) =>
                record.id
                    .toString()
                    .toLowerCase()
                    .includes(value.toLowerCase()),
            onFilterDropdownVisibleChange: visible => {
                if (visible) {
                    setTimeout(() => {
                        this.searchInput.focus();
                    });
                }
            },
        },
        {
            title: '用户邮箱',
            dataIndex: 'email',
            key: 'email',
            scopedSlots: {
                filterDropdown: 'filterDropdown',
                filterIcon: 'filterIcon',
                customRender: 'customRender',
            },
            onFilter: (value, record) =>
                record.email
                    .toString()
                    .toLowerCase()
                    .includes(value.toLowerCase()),
            onFilterDropdownVisibleChange: visible => {
                if (visible) {
                    setTimeout(() => {
                        this.searchInput.focus();
                    });
                }
            },
        },
        {
            title: '用户名',
            dataIndex: 'userName',
            key: 'userName',
            scopedSlots: {
                filterDropdown: 'filterDropdown',
                filterIcon: 'filterIcon',
                customRender: 'customRender',
            },
            onFilter: (value, record) => {
                if (record.userName) {
                    return record.userName.toString().toLowerCase().includes(value.toLowerCase())
                }//搞了半天，是不带括号的箭头函数省略了return
            },
            onFilterDropdownVisibleChange: visible => {
                if (visible) {
                    setTimeout(() => {
                        this.searchInput.focus();
                    });
                }
            },
        },
        /* 加密之后显示密码无意义
        {
            title: '用户密码',
            dataIndex: 'password',
        },
         */
        {
            title: '用户手机号',
            dataIndex: 'phoneNumber',
        },
        {
            title: '信用值',
            dataIndex: 'credit',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    const columns2 = [
        {
            title: '酒店ID',
            dataIndex: 'id',
            key: 'id',
            scopedSlots: {
                filterDropdown: 'filterDropdown',
                filterIcon: 'filterIcon',
                customRender: 'customRender',
            },
            onFilter: (value, record) =>
                record.id
                    .toString()
                    .toLowerCase()
                    .includes(value.toLowerCase()),
            onFilterDropdownVisibleChange: visible => {
                if (visible) {
                    setTimeout(() => {
                        this.searchInput.focus();
                    });
                }
            },
        },
        {
            title: '酒店名',
            dataIndex: 'name',
            key: 'name',
            scopedSlots: {
                filterDropdown: 'filterDropdown',
                filterIcon: 'filterIcon',
                customRender: 'customRender',
            },
            onFilter: (value, record) => {
                if (record.name) {
                    return record.name.toString().toLowerCase().includes(value.toLowerCase())
                }//搞了半天，是不带括号的箭头函数省略了return
            },
            onFilterDropdownVisibleChange: visible => {
                if (visible) {
                    setTimeout(() => {
                        this.searchInput.focus();
                    });
                }
            },
        },
        {
            title: '商圈',
            dataIndex: 'bizRegion',
            key: 'bizRegion',
            scopedSlots: {
                filterDropdown: 'filterDropdown',
                filterIcon: 'filterIcon',
                customRender: 'customRender',
            },
            onFilter: (value, record) => {
                if (record.bizRegion) {
                    return record.bizRegion.toString().toLowerCase().includes(value.toLowerCase())
                }//搞了半天，是不带括号的箭头函数省略了return
            },
            onFilterDropdownVisibleChange: visible => {
                if (visible) {
                    setTimeout(() => {
                        this.searchInput.focus();
                    });
                }
            },
        },
        {
            title: '地址',
            dataIndex: 'address',
        },
        {
            title: '酒店星级',
            dataIndex: 'hotelStar',
            filters: [
                {
                    text: '三星哦',
                    value: 'Three',
                },
                {
                    text: '四星级',
                    value: 'Four',
                },
                {
                    text: '五星级',
                    value: 'Five',
                },
            ],
            onFilter: (value, record) => record.hotelStar.indexOf(value) === 0,
        },
        {
            title: '评分',
            dataIndex: 'rate',
        },
        {
            title: '简介',
            dataIndex: 'description',
        },
        {
            title: '工作人员ID',
            dataIndex: 'managerId',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: {customRender: 'action'},
        },
    ];
    export default {
        name: 'manageUser',
        data() {
            return {
                formLayout: 'horizontal',
                pagination: {},
                columns1,
                columns2,
                data: [],
                form: this.$form.createForm(this, {name: 'manageUser'}),


                searchText: '',
                searchInput: null,
                searchedColumn: '',
            };
        },
        components: {
            AddManagerModal,
            AddHotelModal,
        },
        computed: {
            ...mapGetters([
                'addManagerModalVisible',
                'addHotelModalVisible',
                'managerList',
                'hotelList',
                'currentHotelId',
                'salesmanList',
                'normalUserList',
            ])
        },
        async mounted() { // 取得酒店、普通用户、酒店工作人员、网站营销人员的列表
            await this.getManagerList()
            await this.getHotelList()
            await this.getSalesmanList()
            await this.getNormalUserList()
        },

        methods: {
            ...mapActions([
                'getManagerList',
                'deleteHotelManagerA',
                'getHotelList',
                'deleteHotelA',
                'getSalesmanList',
                'deleteSalesmanA',
                "getNormalUserList",
                "deleteNormalUser",
            ]),
            ...mapMutations([
                'set_addManagerModalVisible',
                'set_addHotelModalVisible',
                'set_currentHotelId',
                "set_addManagerType",
            ]),
            addManager() {
                this.set_addManagerModalVisible(true)
            },
            //迭代3.1增
            deleteHotelManager(record) {
                this.deleteHotelManagerA(record.id)
            },
            deleteSalesman(record) {
                this.deleteSalesmanA(record.id)
            },
            //迭代3.3 移植
            addHotel() {
                this.set_addHotelModalVisible(true)
            },
            deleteHotel(record) {
                this.deleteHotelA(record.id)
            },
            //迭代3.3 新增
            // 管理员在酒店记录上点击添加酒店工作人员出发，传入那条酒店记录
            addManagerOfThisHotel(record) {
                this.set_currentHotelId(record.id)
                this.set_addManagerType("HotelManager")
                this.set_addManagerModalVisible(true)
            },
            // 添加网站营销人员，和添加酒店工作人员共用一个modal
            addSalesman(record) {
                this.set_addManagerType("Salesman")
                this.set_addManagerModalVisible(true)
            },
            //迭代3.7
            deleteUser(record) { //删除普通用户
                this.deleteNormalUser(record.id)
            },

            // table搜索的处理函数
            handleSearch(selectedKeys, confirm, dataIndex) {
                confirm();
                this.searchText = selectedKeys[0];
                this.searchedColumn = dataIndex;
            },

            handleReset(clearFilters) {
                clearFilters();
                this.searchText = '';
            },
        },
    }
</script>
<style scoped lang="less">
    .manageUser-wrapper {
        padding: 50px;

        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageUser-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }


    .highlight {
        background-color: rgb(255, 192, 105);
        padding: 0px;
    }
</style>
<style lang="less">

</style>