<template>
  <div class="m-4 mr-0 overflow-hidden bg-white">
    <BasicTree
      title="部门列表"
      toolbar
      search
      treeWrapperClassName="h-[calc(100%-35px)] overflow-auto"
      :clickRowToExpand="false"
      :treeData="treeData"
      :fieldNames="{ key: 'id', title: 'deptName' }"
      @select="handleSelect"
    />
  </div>
</template>
<script lang="ts" setup>
  import { onMounted, ref } from 'vue';

  import { BasicTree, TreeItem } from '@/components/Tree';
  import { enableTreeApi } from '@/api/system/dept';

  defineOptions({ name: 'DeptTree' });

  const props = defineProps({
    // 是否扩展同级
    extends: {
      type: Number,
      default: 0,
    },
  });

  const emit = defineEmits(['select']);

  const treeData = ref<Recordable[]>([]);

  async function fetch() {
    treeData.value = [];
    if (props.extends == 1) {
      treeData.value = [{ id: 0, deptName: '全局' }];
    }
    treeData.value = treeData.value.concat((await enableTreeApi()) as unknown as TreeItem[]);
  }

  function handleSelect(keys) {
    emit('select', keys[0]);
  }

  onMounted(() => {
    fetch();
  });
</script>
