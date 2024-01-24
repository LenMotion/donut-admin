<template>
  <PageWrapper contentFullHeight>
    <Description
      title="系统信息"
      :schema="systemDescItems"
      :data="dataInfo.serverInfo"
      :column="2"
    />
    <Description
      class="mt-20px"
      title="JVM信息"
      :schema="jvmDescItems"
      :data="dataInfo.jdkInfo"
      :column="2"
    />

    <Row :gutter="20" class="mt-20px">
      <Col :span="12">
        <Card title="系统内存使用(GB)">
          <div ref="systemMemeyRef" class="h-400px"></div>
        </Card>
      </Col>
      <Col :span="12">
        <Card title="JDK内存使用(MB)">
          <div ref="jdkMemeyRef" class="h-400px"></div>
        </Card>
      </Col>
    </Row>

    <Card title="磁盘状态" :bordered="false" class="mt-20px">
      <BasicTable @register="registerDiskTable" />
    </Card>
  </PageWrapper>
</template>

<script setup lang="ts">
  import { reactive, ref } from 'vue';
  import { PageWrapper } from '@/components/Page';
  import { Description } from '@/components/Description';
  import { systemDescItems, jvmDescItems, memoryOptions, diskTableSchema } from './server.data';
  import * as echarts from 'echarts';

  import { serverInfoApi } from '@/api/monitor/serverInfo';
  import { Row, Col, Card } from 'ant-design-vue';
  import { BasicTable, useTable } from '@/components/Table';

  defineOptions({ name: 'ServerMonitor' });

  const dataInfo = reactive({
    serverInfo: {},
    jdkInfo: {},
  });

  const [registerDiskTable, { setTableData: setDiskTableData }] = useTable({
    dataSource: [],
    columns: diskTableSchema,
    pagination: false,
    showIndexColumn: false,
    showSummary: false,
    canResize: false,
  });

  const systemMemeyRef = ref<HTMLElement | null>(null);
  const jdkMemeyRef = ref<HTMLElement | null>(null);

  serverInfoApi().then((res) => {
    dataInfo.serverInfo = res.systemInfo;
    dataInfo.jdkInfo = res.jvmInfo;
    setDiskTableData(res.fileStoreList);

    const systemMemeyInstance = echarts.init(systemMemeyRef.value, 'macarons');
    const jdkMemeyInstance = echarts.init(jdkMemeyRef.value, 'macarons');
    const { toTal, free } = res.systemInfo;
    const used = parseFloat(((toTal - free) / 1024).toFixed(2));
    systemMemeyInstance.setOption(
      memoryOptions(parseFloat((toTal / 1024).toFixed(1)), used, '#FF6600'),
    );

    const { maxMemory, useMemory } = res.jvmInfo;
    jdkMemeyInstance.setOption(
      memoryOptions(
        parseFloat((maxMemory / 1048576).toFixed(0)),
        parseFloat((useMemory / 1048576).toFixed(0)),
        '#58D9F9',
      ),
    );
  });
</script>
