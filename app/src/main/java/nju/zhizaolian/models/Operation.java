package nju.zhizaolian.models;

/**
 * Created by lk on 15/4/24.
 */
public enum Operation {
     MERGEPRICE,//合并报价  MergePriceFragment
     QUOTEAGREED, //报价商定 QuoteAgreedFragment
     CHANGEQUOTE, //ChangeQuoteFragment 修改报价
      SIGNCONTRACT,  //SignContractFragment 签订合同
      URGEREMAININGBALANCE , //UrgeRemainingBalance 催尾款
      CHECKQUOTE , //CheckQuoteFragment 审核报价
       CHECKSAMPLEBALANCE,     //CheckSampleBalanceFragment 样衣费确认
    CHECKFRONTMONEY,//CheckFrontMoneyFragment 首定金确认
    RETURNMONEY,//ReturnMoneyFragment  退还定金
    CHECKREMAININGBALANCE,// CheckRemainingBalanceFragment 尾款金确认
    RECEIVESAMPLE,//ReceiveSampleFragment 样衣收取
    DELIVERSAMPLE,//DeliverSampleFragment 样衣发货
    CHECKQALITY//CheckQualityFragment 质量检查

}
