<template>
  <BasicDrawer
    v-bind="$attrs"
    @register="registerDrawer"
    title="操作详情"
    :show-footer="false"
    width="600px"
  >
    <Description :column="1" :schema="detailDescItems" :data="optData" />
  </BasicDrawer>
</template>
<script lang="ts" setup>
  import { ref } from 'vue';
  import { BasicDrawer, useDrawerInner } from '@/components/Drawer';
  import { Description } from '@/components/Description';
  import { detailDescItems } from './operationLog.data';

  defineEmits(['register']);

  const optData = ref({});

  const [registerDrawer, { setDrawerProps }] = useDrawerInner(async (data) => {
    setDrawerProps({ confirmLoading: false });
    optData.value = data.record;
  });
</script>
