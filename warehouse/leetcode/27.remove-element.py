class Solution(object):
    def removeElement(self, nums, val):
        """
        :type nums: List[int]
        :type val: int
        :rtype: int
        """
        i = 0
        e = len(nums)-1

        while i<=e:
            while e>=0 and nums[e]==val:
                e-=1
            while i<=e and nums[i]!=val:
                i+=1

            if i<=e and nums[i]==val:
                nums[i]=nums[e]
                i+=1
                e-=1
        # print(nums[:e+1])
        return e+1


# a = Solution()
# print(a.removeElement([1,2,3,4,5],2))
# print(a.removeElement([3,2,2,3],3))
# print(a.removeElement([],3))
# print(a.removeElement([3],3))
# print(a.removeElement([2],3))
| 标号 | 字段                    | 中文                                      | 值   |
| :--: | :--:                    | :--:                                      | :--: |
|    1 | user_id	             | 	用户编号	                          |      |
|    2 | acct_id                 | 	帐户标识	                          |      |
|    3 | eparchy_code            | 	地市编码	                          |      |
|    4 | city_code	           | 	区县编码	                          |      |
|    5 | cust_id                 | 	客户编号	                          |      |
|    6 |                         | 	客户名称	                          |      |
|    7 | sex                     | 	客户性别	                          |      |
|    8 |                         | 	生日	                              |      |
|    9 | age                     | 	年龄	                              |      |
|   10 |                         | 		证件类型	                      |      |
|   11 |                         | 		证件号	                        |      |
|   12 |                         | 		证件地址	                      |      |
|   13 |                         | 		联系人电话	                    |      |
|   14 |                         | 		联系地址	                      |      |
|   15 |                         | 		手机号码	                      |      |
|   16 | mainsvctype             | 		产品编码	                      |      |
|   17 | main_feeset             | 		主套餐标识	                    |      |
|   18 | vip_type_code           | 		重要大客户代码	                |      |
|   19 | vip_class               | 		大客户级别编码	                |      |
|   20 | score_value             | 		积分值	                        |      |
|   21 | basic_credit_value      | 		基本信用度	                    |      |
|   22 | open_date               | 		开户日期	                      |      |
|   23 |                         | 		首次通话时间	                  |      |
|   24 | custlevel               | 		客户级别	                      |      |
|   25 | stop_flag               | 		停机标志	                      |      |
|   26 | arr_user_flag           | 		用户到达标签	                  |      |
|   27 | vip_flag                | 		大客户标签	                    |      |
|   28 | local_bill_dura         | 		本地计费时长	                  |      |
|   29 | mo_long_bill_dura       | 		主叫长途计费时长	              |      |
|   30 | mo_roam_bill_dura       | 		主叫漫游计费时长	              |      |
|   31 | mo_local_fee            | 		本地主叫费用	                  |      |
|   32 | mo_local_long_fee       | 		本地国内长途主叫费	            |      |
|   33 | roam_fee                | 		国内漫游费	                    |      |
|   34 | mo_prov_roam_fee        | 		其中：省内漫游主叫费用	        |      |
|   35 | mt_prov_roam_fee        | 		其中：省内漫游被叫费用	        |      |
|   36 | mo_outprov_roam_fee     | 		其中：省际漫游主叫费用	        |      |
|   37 | mt_outprov_roam_fee     | 		其中：省际漫游被叫费用	        |      |
|   38 | l0_fee                  | 		用户账单费用	                  |      |
|   39 | total_flow              | 		月使用流量	                    |      |
|   40 | lte_flow                | 		4G流量	                        |      |
|   41 | busy_flow               | 		忙时流量	                      |      |
|   42 | idle_flow               | 		闲时流量	                      |      |
|   43 | gprs_month_fee          | 		流量包月费	                    |      |
|   44 | gprs_over_fee           | 		流量超套餐费	                  |      |
|   45 |                         | 		微信使用次数	                  |      |
|   46 |                         | 		微信使用流量	                  |      |
|   47 |                         | 		微信使用4G流量	                |      |
|   48 | qq_cnt                  | 		QQ使用次数	                    |      |
|   49 | qq_flow                 | 		QQ使用流量	                    |      |
|   50 | qq_lte_flow             | 		QQ使用4G流量	                  |      |
|   51 | alipay_cnt              | 		支付宝使用次数	                |      |
|   52 | alipay_flow             | 		支付宝使用流量	                |      |
|   53 | alipay_flow             | 		支付宝使用4G流量	              |      |
|   54 | sina_weibo_cnt          | 		新浪微博使用次数	              |      |
|   55 | sina_weibo_flow         | 		新浪微博使用流量	              |      |
|   56 | sina_weibo_lte_flow     | 		新浪微博使用4G流量	            |      |
|   57 | migu_music_cnt          | 		咪咕音乐使用次数	              |      |
|   58 | migu_music_flow         | 		咪咕音乐使用流量	              |      |
|   59 | migu_music_lte_flow     | 		咪咕音乐使用4G流量	            |      |
|   60 | migu_video_cnt          | 		咪咕视频使用次数	              |      |
|   61 | migu_video_flow         | 		咪咕视频使用流量	              |      |
|   62 | migu_video_lte_flow     | 		咪咕视频使用4G流量	            |      |
|   63 | migu_read_cnt           | 		咪咕阅读使用次数	              |      |
|   64 | migu_read_flow          | 		咪咕阅读使用流量	              |      |
|   65 | migu_read_lte_flow      | 		咪咕阅读使用4G流量	            |      |
|   66 | migu_animation_cnt      | 		咪咕动漫使用次数	              |      |
|   67 | migu_animation_flow     | 		咪咕动漫使用流量	              |      |
|   68 | migu_animation_lte_flow | 		咪咕动漫使用4G流量	            |      |
|   69 | migu_play_cnt           | 		手机支付使用存储	              |      |
|   70 | migu_play_flow          | 		手机支付使用流量	              |      |
|   71 | migu_play_lte_flow      | 		手机支付使用4G流量	            |      |
|   72 | tenct_video_cnt         | 		腾讯视频使用次数	              |      |
|   73 | tenct_video_flow        | 		腾讯视频使用流量	              |      |
|   74 | tenct_video_lte_flow    | 		腾讯视频使用4G流量	            |      |
|   75 | qq_music_cnt            | 		QQ音乐使用次数	                |      |
|   76 | qq_music_flow           | 		QQ音乐使用流量	                |      |
|   77 | qq_music_lte_flow       | 		QQ音乐使用4G流量	              |      |
|   78 | king_glory_cnt          | 		王者荣耀使用次数	              |      |
|   79 | king_glory_flow         | 		王者荣耀使用流量	              |      |
|   80 | king_glory_lte_flow     | 		王者荣耀使用4G流量	            |      |
|   81 | tenct_apply_cnt         | 		腾讯支付使用次数	              |      |
|   82 | tenct_apply_flow        | 		腾讯支付使用流量	              |      |
|   83 | tenct_apply_lte_flow    | 		腾讯支付使用4G流量	            |      |
|   84 | qq_read_cnt             | 		QQ阅读使用次数	                |      |
|   85 | qq_read_flow            | 		QQ阅读使用流量	                |      |
|   86 | qq_read_lte_flow        | 		QQ阅读使用4G流量	              |      |
|   87 | imei                    | 		imei号码	                      |      |
|   88 | phone_quality_code      | 		终端品牌	                      |      |
|   89 | phone_model_code        | 		终端型号	                      |      |
|   90 | phone_price             | 		终端价位	                      |      |
|   91 | app_name                | 		占用4G流量最多的APP名称	       |      |
|   92 | flow                    | 		占用4G流量最多的APP使用4G流量	 |      |
