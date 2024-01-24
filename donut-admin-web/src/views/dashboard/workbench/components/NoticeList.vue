<template>
  <div>
    <Card title="最新公告" v-bind="$attrs">
      <template #extra>
        <a-button type="link" @click="goMore"> 更多</a-button>
      </template>
      <List item-layout="horizontal" :data-source="list" :locale="{ emptyText: '暂无公告' }">
        <template #renderItem="{ item }">
          <ListItem @click="handleDetail(item)">
            <ListItemMeta>
              <template #description>
                {{ item.publishTime }}
              </template>
              <!-- eslint-disable-next-line -->
              <template #title> {{ item.noticeTitle }} <span v-html="item.desc"> </span> </template>
            </ListItemMeta>
          </ListItem>
        </template>
      </List>
    </Card>
    <NoticeDetailModal @register="registerModal" />
  </div>
</template>
<script lang="ts" setup>
  import { Card, List } from 'ant-design-vue';
  import { userNoticeListApi } from '@/api/system/notice';
  import { ref } from 'vue';
  import NoticeDetailModal from '@/components/Donut/NoticeDetailModal/index.vue';
  import { useModal } from '@/components/Modal';
  import { useRouter } from 'vue-router';

  const router = useRouter();
  const list = ref([]);
  userNoticeListApi({ pageSize: 5, noticeType: 1 }).then((res) => {
    list.value = res.items;
  });

  const ListItem = List.Item;
  const ListItemMeta = List.Item.Meta;

  const [registerModal, { openModal }] = useModal();

  const handleDetail = (notice) => {
    openModal(true, { notice });
  };

  const goMore = () => {
    router.push({ name: 'UserNoticeList', query: { noticeType: '1' } });
  };
</script>
