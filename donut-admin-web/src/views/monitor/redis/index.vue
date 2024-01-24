<template>
  <div>
    <PageWrapper contentFullHeight>
      <Description
        size="middle"
        title="Redis缓存信息"
        :column="3"
        :data="cache.info"
        :schema="schemas"
      />
      <Row :gutter="20" style="margin-top: 20px">
        <Col :span="12">
          <Card title="命令统计" :bordered="false">
            <div ref="commandstats" style="height: 420px"></div>
          </Card>
        </Col>
        <Col :span="12">
          <Card title="内存信息" :bordered="false">
            <div ref="usedmemory" style="height: 420px"></div>
          </Card>
        </Col>
      </Row>
    </PageWrapper>
  </div>
</template>
<script lang="ts" setup>
  import { Description } from '@/components/Description/index';
  import { PageWrapper } from '@/components/Page';
  import { reactive, ref } from 'vue';
  import * as echarts from 'echarts';
  import { Card, Row, Col } from 'ant-design-vue';

  import { redisInfoApi } from '@/api/monitor/serverInfo';
  import { schemas } from './redis.data';

  defineOptions({ name: 'RedisMonitor' });

  const cache = reactive({
    info: { dbSize: 0 },
  });
  const commandstats = ref<any>(null);
  const usedmemory = ref<any>(null);

  redisInfoApi().then((res) => {
    const { dbSize, properties, cmdStats } = res;
    cache.info = properties;
    cache.info.dbSize = dbSize;

    const commandstatsIntance = echarts.init(commandstats.value, 'macarons');
    commandstatsIntance.setOption({
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)',
      },
      series: [
        {
          name: '命令',
          type: 'pie',
          roseType: 'radius',
          radius: [15, 95],
          center: ['50%', '38%'],
          data: cmdStats,
          animationEasing: 'cubicInOut',
          animationDuration: 1000,
        },
      ],
    });

    const usedmemoryInstance = echarts.init(usedmemory.value, 'macarons');
    usedmemoryInstance.setOption({
      tooltip: {
        formatter: '{b} <br/>{a} : ' + properties.used_memory_human,
      },
      series: [
        {
          name: '峰值',
          type: 'gauge',
          min: 0,
          max: 1000,
          detail: {
            formatter: properties.used_memory_human,
          },
          data: [
            {
              value: parseFloat(properties.used_memory_human),
              name: '内存消耗',
            },
          ],
        },
      ],
    });
  });
</script>
