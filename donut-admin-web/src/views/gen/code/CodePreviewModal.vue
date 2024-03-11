<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    title="预览代码"
    :mask-closable="false"
    width="70%"
  >
    <Tabs v-model:activeKey="activeKey" @change="changeActive">
      <TabPane key="java/Po.java.btl" tab="实体类" />
      <TabPane key="java/Request.java.btl" tab="请求实体类" />
      <TabPane key="java/Query.java.btl" tab="查询类" />
      <TabPane key="java/Converter.java.btl" tab="转换器" />
      <TabPane key="java/Mapper.java.btl" tab="Mapper" />
      <TabPane key="java/Service.java.btl" tab="Service" />
      <TabPane key="java/ServiceImpl.java.btl" tab="ServiceImpl" />
      <TabPane key="java/Controller.java.btl" tab="Controller" />
      <TabPane key="sql/menu.sql.btl" tab="sql" />
      <TabPane key="vue/index.vue.btl" tab="index.vue" />
      <TabPane key="vue/data.ts.btl" tab="data.ts" />
      <TabPane key="vue/Drawer.vue.btl" tab="Drawer.vue" />
      <TabPane key="vue/api.ts.btl" tab="api.ts" />
    </Tabs>

    <Textarea type="textarea" v-model:value="code" autosize />
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { Tabs, TabPane, Textarea } from 'ant-design-vue';
  import { previewApi } from '@/api/gen/code';

  defineEmits(['register']);

  const activeKey = ref('java/Po.java.btl');
  const record = ref({ id: 0 });
  const code = ref<any>('');

  const [registerModal] = useModalInner((data) => {
    record.value = data.record;
    activeKey.value = 'java/Po.java.btl';
    changeActive();
  });

  const changeActive = () => {
    previewApi({ tableId: record.value.id, fileName: activeKey.value }).then((res) => {
      console.log(res);
      code.value = res;
    });
  };
</script>
