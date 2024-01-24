<template>
  <div>
    <List :class="prefixCls" bordered :pagination="getPagination">
      <template v-for="item in dataList" :key="item.id">
        <List.Item class="list-item">
          <List.Item.Meta>
            <template #title>
              <div class="title">
                <Typography.Paragraph
                  @click="handleTitleClick(item)"
                  :ellipsis="
                    titleRows && titleRows > 0
                      ? { rows: titleRows, tooltip: !!item.noticeTitle }
                      : false
                  "
                  :content="item.noticeTitle"
                />
                <div class="extra" v-if="item.extra">
                  <Tag class="tag" :color="item.color">
                    {{ item.extra }}
                  </Tag>
                </div>
              </div>
            </template>

            <template #description>
              <div class="datetime">
                {{ item.publishTime }}
              </div>
            </template>
          </List.Item.Meta>
        </List.Item>
      </template>
    </List>
    <NoticeDetailModal @register="registerModal" />
  </div>
</template>
<script lang="ts" setup>
  import { computed, PropType, ref, watch, unref } from 'vue';
  import { ListItem } from './data';
  import { useDesign } from '@/hooks/web/useDesign';
  import { List, Tag, Typography } from 'ant-design-vue';
  import { isNumber } from '@/utils/is';
  import NoticeDetailModal from '@/components/Donut/NoticeDetailModal/index.vue';
  import { useModal } from '@/components/Modal';

  const props = defineProps({
    list: {
      type: Array as PropType<ListItem[]>,
      default: () => [],
    },
    pageSize: {
      type: [Boolean, Number] as PropType<Boolean | Number>,
      default: 5,
    },
    currentPage: {
      type: Number,
      default: 1,
    },
    total: {
      type: Number,
      default: 0,
    },
    titleRows: {
      type: Number,
      default: 1,
    },
    descRows: {
      type: Number,
      default: 2,
    },
    onTitleClick: {
      type: Function as PropType<(Recordable) => void>,
    },
  });

  const [registerModal, { openModal }] = useModal();

  const emit = defineEmits(['update:currentPage']);

  const { prefixCls } = useDesign('header-notify-list');
  const current = ref(props.currentPage || 1);
  const dataList = ref(props.list || []);
  // const getData = computed(() => {
  //   const { pageSize, list } = props;
  //   if (pageSize === false) return [];
  //   let size = isNumber(pageSize) ? pageSize : 5;
  //   return list.slice(size * (unref(current) - 1), size * unref(current));
  // });
  watch(
    () => props.currentPage,
    (v) => {
      current.value = v;
    },
  );
  watch(
    () => props.list,
    (v) => {
      dataList.value = v;
    },
  );
  const getPagination = computed(() => {
    const { total, pageSize } = props;

    // compatible line 104
    // if typeof pageSize is boolean, Number(true) && 5 = 5, Number(false) && 5 = 0
    const size = isNumber(pageSize) ? pageSize : Number(pageSize) && 5;

    // if (size > 0 && list && list.length > size) {
    // } else {
    //   return false;
    // }
    return {
      total: total,
      pageSize: size,
      current: unref(current),
      size: 'small',
      onChange(page) {
        current.value = page;
        emit('update:currentPage', page);
      },
    };
  });

  function handleTitleClick(item: ListItem) {
    openModal(true, { notice: item });
  }
</script>
<style lang="less" scoped>
  @prefix-cls: ~'@{namespace}-header-notify-list';

  .@{prefix-cls} {
    &::-webkit-scrollbar {
      display: none;
    }

    ::v-deep(.ant-pagination-disabled) {
      display: inline-block !important;
    }

    .list-item {
      padding: 6px;
      overflow: hidden;
      transition: all 0.3s;
      cursor: pointer;

      .title {
        margin-bottom: 8px;
        font-weight: normal;

        .extra {
          margin-top: -1.5px;
          margin-right: 0;
          float: right;
          font-weight: normal;

          .tag {
            margin-right: 0;
          }
        }
      }

      .avatar {
        margin-top: 4px;
      }

      .description {
        font-size: 12px;
        line-height: 18px;
      }

      .datetime {
        margin-top: 4px;
        font-size: 12px;
        line-height: 18px;
      }
    }
  }
</style>
