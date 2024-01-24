<template>
  <Card title="操作日志" v-bind="$attrs">
    <List item-layout="horizontal" :data-source="list">
      <template #renderItem="{ item }">
        <ListItem>
          <ListItemMeta>
            <template #description>
              {{ item.createTime }}
            </template>
            <!-- eslint-disable-next-line -->
            <template #title> {{ item.title }} <span v-html="item.desc"> </span> </template>
            <template #avatar>
              <Avatar :src="avatarUrl" :size="30" />
            </template>
          </ListItemMeta>
        </ListItem>
      </template>
    </List>
  </Card>
</template>
<script lang="ts" setup>
  import { Card, List } from 'ant-design-vue';
  import { Avatar } from 'ant-design-vue';
  import { userOperationLogListApi } from '@/api/system/log';
  import { ref } from 'vue';

  import { useUserStore } from '@/store/modules/user';

  const userStore = useUserStore();

  const { avatarUrl } = userStore.getUserInfo;
  console.log(avatarUrl)

  const list = ref([]);
  userOperationLogListApi({ pageSize: 10 }).then((res) => {
    list.value = res.items;
  });

  const ListItem = List.Item;
  const ListItemMeta = List.Item.Meta;
</script>
