import { defineStore } from 'pinia';
import { loginPageApi } from '@/api/system/config';
import logo from '@/assets/images/logo.png';
import { store } from '@/store';

import type { SiteInfo } from '#/store';

export const useSiteInfoStore = defineStore({
  id: 'siteInfo',
  state: (): SiteInfo => ({
    name: 'Donut-Admin',
    title: '甜甜圈通用管理系统',
    logo,
    logoUrl: '',
    description: '一款基于SpringBoot3、JDK17、SaToken、MybatisPlus的开源管理系统。',
  }),
  getters: {
    getName(state) {
      return state.name;
    },
    getTitle(state) {
      return state.title;
    },
    getLogo(state) {
      return state.logo;
    },
    getDescription(state) {
      return state.description;
    },
    siteInfo(state) {
      return {
        name: state.name,
        title: state.title,
        logo: state.logo,
        logoUrl: state.logoUrl || logo,
        description: state.description,
      };
    },
  },
  actions: {
    getSiteInfo() {
      loginPageApi().then((res) => {
        this.name = res.name;
        this.title = res.title;
        this.logo = res.logo;
        this.description = res.description;
      });
    },
  },
});

// Need to be used outside the setup
export function useSiteInfoStoreWithOut() {
  return useSiteInfoStore(store);
}
