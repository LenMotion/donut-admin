<template>
  <div>
    <Switch
      :checked="status"
      checked-value="0"
      un-checked-value="1"
      :loading="loading"
      :disabled="disabled"
      @change="handleStatusChange"
    />
  </div>
</template>

<script setup lang="ts">
  import { Switch } from 'ant-design-vue';
  import { isFunction } from '@/utils/is';
  import { PropType, computed, ref } from 'vue';
  import { usePermission } from '@/hooks/web/usePermission';

  const emit = defineEmits(['success']);

  const { hasPermission } = usePermission();

  const props = defineProps({
    value: {
      type: String,
      required: true,
    },
    id: {
      type: String,
      required: true,
    },
    api: {
      type: Function as PropType<(arg?: any) => Promise<boolean>>,
      required: true,
    },
    permCode: {
      type: String,
      default: '*',
    },
  });

  const status = computed(() => props.value);
  const disabled = computed(() => props.permCode !== '*' && !hasPermission(props.permCode));
  const loading = ref(false);

  const handleStatusChange = async (status) => {
    const api = props.api;
    if (!api || !isFunction(api)) {
      console.error('not function');
      return;
    }

    loading.value = true;
    const data = { id: props.id, status };
    try {
      const res = await props.api(data);
      console.log(res);
      emit('success', status);
    } catch (e) {
      console.error(e);
      /* empty */
    } finally {
      loading.value = false;
    }
  };
</script>
