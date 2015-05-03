package nju.zhizaolian.models;

/**
 * Created by lk on 15/4/24.
 */
public enum Operation {

    //市场部
    MERGEPRICE,//合并报价  MergePriceFragment
    QUOTEAGREED, //报价商定 QuoteAgreedFragment
    CHANGEQUOTE, //ChangeQuoteFragment 修改报价
    SIGNCONTRACT,  //SignContractFragment 签订合同
    URGEREMAININGBALANCE , //UrgeRemainingBalance 催尾款

    CHECKQUOTE , //CheckQuoteFragment 审核报价 市场主管

    //财务部
    CHECKSAMPLEBALANCE,     //CheckSampleBalanceFragment 样衣费确认
    CHECKFRONTMONEY,//CheckFrontMoneyFragment 首定金确认
    RETURNMONEY,//ReturnMoneyFragment  退还定金
    CHECKREMAININGBALANCE,// CheckRemainingBalanceFragment 尾款金确认

    //物流部
    RECEIVESAMPLE,//ReceiveSampleFragment 样衣收取
    DELIVERSAMPLE,//DeliverSampleFragment 样衣发货
    WAREHOUSE, //warehouseEntryFragment 产品入库
    WAREHOUSEREGISTER,//入库登记
    SENDCLOTHES,//产品发货
    SENDCLOTHESSCAN,//发货扫描
    //质检部
    CHECKQALITY,//CheckQualityFragment 质量检查

    DESIGN_SLICE,
    DESIGN_ENTERING,
    DESIGN_CONFIRM,

    TECHNOLOGY_DESIGN,
    TECHNOLOGY_MASS,
    TECHNOLOGY_SAMPLE,

    PURCHASE_CHECK,
    PURCHASE_SAMPLE,
    PURCHASE_MASS,
    PURCHASE_SWEATER,

    PRODUCTION_CHECK,
    PRODUCTION_MASS,

    SWEATER_MAKER_CHECK,
    SWEATER_MAKER_SENT,

    ORDER_MANAGER_DETAIL,

    SECRETARY_CHANGE

}
