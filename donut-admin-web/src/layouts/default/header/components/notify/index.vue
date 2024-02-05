<template>
  <div :class="prefixCls">
    <Popover title="" trigger="click" :overlayClassName="`${prefixCls}__overlay`">
      <Badge :count="count" dot :numberStyle="numberStyle">
        <BellOutlined />
      </Badge>
      <template #content>
        <Tabs>
          <template v-for="item in listData" :key="item.key">
            <Tabs.TabPane>
              <template #tab>
                {{ item.name }}
                <span v-if="count !== 0">({{ count }})</span>
              </template>
              <!-- 绑定title-click事件的通知列表中标题是“可点击”的-->
              <NoticeList
                :list="item.list"
                v-model:current-page="pageInfo.currentPage"
                :page-size="pageInfo.pageSize"
                :title-rows="pageInfo.titleRows"
                :total="pageInfo.total"
                @title-click="onNoticeClick"
              />
            </Tabs.TabPane>
          </template>
        </Tabs>
      </template>
    </Popover>
    <NoticeDetailModal @register="registerModal" />
  </div>
</template>
<script lang="ts" setup>
  import { computed, ref, watchEffect, onBeforeUnmount, onMounted, watch, reactive } from 'vue';
  import { Popover, Tabs, Badge } from 'ant-design-vue';
  import { BellOutlined } from '@ant-design/icons-vue';
  import { ListItem } from './data';
  import NoticeList from './NoticeList.vue';
  import { useDesign } from '@/hooks/web/useDesign';
  import { useMessage } from '@/hooks/web/useMessage';
  import { useWebSocket } from '@vueuse/core';
  import { useGlobSetting } from '@/hooks/setting';
  import { getToken } from '@/utils/auth';
  import { userNoticeListApi } from '@/api/system/notice';
  import NoticeDetailModal from '@/components/Donut/NoticeDetailModal/index.vue';
  import { useModal } from '@/components/Modal';
  import { useUserStore } from '@/store/modules/user';

  const { wsUrl } = useGlobSetting();
  const userStore = useUserStore();
  const { createMessage, notification } = useMessage();
  const [registerModal, { openModal }] = useModal();

  console.log('start websocket');
  const { status, data, close, open } = useWebSocket(wsUrl + '/ws/notice/' + getToken(), {
    autoReconnect: {
      retries: () => {
        const token = getToken();
        return token != undefined && token != '';
      },
    },
    immediate: false,
    heartbeat: {
      message: 'heartbeat',
      interval: 10000,
    },
  });
  const getIsOpen = computed(() => status.value === 'OPEN');

  const { prefixCls } = useDesign('header-notify');
  const numberStyle = {};

  const count = ref(0);
  const listData = ref<any[]>([]);
  const pageInfo = reactive({
    total: 0,
    currentPage: 1,
    pageSize: 10,
    titleRows: 1,
  });

  watch(
    () => pageInfo.currentPage,
    (value) => {
      getNoticeList(value);
    },
  );

  const getNoticeList = (pageNum: number) => {
    userNoticeListApi({ read: false, noticeType: '0', pageSize: 5, pageNum }).then((res) => {
      count.value = res.total;
      console.log(count.value);
      pageInfo.total = res.total;
      pageInfo.currentPage = res.pageNum;
      pageInfo.pageSize = res.pageSize;
      listData.value = [
        {
          key: '1',
          name: '通知',
          list: res.items,
        },
      ];
    });
  };

  onMounted(() => {
    if (!getIsOpen.value) {
      console.log('开启链接ws');
      open();
    }
    getNoticeList(1);
  });

  onBeforeUnmount(() => {
    console.log('关闭链接ws');
    close();
  });

  watchEffect(() => {
    if (data.value) {
      try {
        const { result, code, msg } = JSON.parse(data.value);

        if (code === 200) {
          console.log(result);
          notification.success({
            message: '新消息通知',
            description: result.noticeTitle,
            duration: 3,
            onClick: () => {
              openModal(true, { notice: result });
            },
          });
          getNoticeList(1);
        } else if (code === 1000) {
          // 心跳响应，不做处理
        } else if (code === 1401) {
          userStore.logout();
          createMessage.error(msg);
        } else {
          createMessage.error(msg);
        }
      } catch (e) {
        console.error(e);
      }
    }
  });

  function onNoticeClick(record: ListItem) {
    createMessage.success('你点击了通知，ID=' + record.id);
    // 可以直接将其标记为已读（为标题添加删除线）,此处演示的代码会切换删除线状态
    record.titleDelete = !record.titleDelete;
  }
</script>
<style lang="less">
  @prefix-cls: ~'@{namespace}-header-notify';

  .@{prefix-cls} {
    padding-bottom: 1px;

    &__overlay {
      width: 300px;
    }

    .ant-tabs-content {
      width: 300px;
    }

    .ant-badge {
      display: flex;
      align-items: center;
      font-size: 18px;

      .ant-badge-multiple-words {
        padding: 0 4px;
      }

      svg {
        width: 0.9em;
      }
    }
  }
</style>
