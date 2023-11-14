package only.leo.wfm.common.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndexDirectoryDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IndexDirectoryDOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Short value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Short value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Short value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Short value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Short value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Short value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Short> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Short> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Short value1, Short value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Short value1, Short value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPathIsNull() {
            addCriterion("path is null");
            return (Criteria) this;
        }

        public Criteria andPathIsNotNull() {
            addCriterion("path is not null");
            return (Criteria) this;
        }

        public Criteria andPathEqualTo(String value) {
            addCriterion("path =", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotEqualTo(String value) {
            addCriterion("path <>", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathGreaterThan(String value) {
            addCriterion("path >", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathGreaterThanOrEqualTo(String value) {
            addCriterion("path >=", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLessThan(String value) {
            addCriterion("path <", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLessThanOrEqualTo(String value) {
            addCriterion("path <=", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLike(String value) {
            addCriterion("path like", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotLike(String value) {
            addCriterion("path not like", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathIn(List<String> values) {
            addCriterion("path in", values, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotIn(List<String> values) {
            addCriterion("path not in", values, "path");
            return (Criteria) this;
        }

        public Criteria andPathBetween(String value1, String value2) {
            addCriterion("path between", value1, value2, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotBetween(String value1, String value2) {
            addCriterion("path not between", value1, value2, "path");
            return (Criteria) this;
        }

        public Criteria andListenIsNull() {
            addCriterion("listen is null");
            return (Criteria) this;
        }

        public Criteria andListenIsNotNull() {
            addCriterion("listen is not null");
            return (Criteria) this;
        }

        public Criteria andListenEqualTo(Boolean value) {
            addCriterion("listen =", value, "listen");
            return (Criteria) this;
        }

        public Criteria andListenNotEqualTo(Boolean value) {
            addCriterion("listen <>", value, "listen");
            return (Criteria) this;
        }

        public Criteria andListenGreaterThan(Boolean value) {
            addCriterion("listen >", value, "listen");
            return (Criteria) this;
        }

        public Criteria andListenGreaterThanOrEqualTo(Boolean value) {
            addCriterion("listen >=", value, "listen");
            return (Criteria) this;
        }

        public Criteria andListenLessThan(Boolean value) {
            addCriterion("listen <", value, "listen");
            return (Criteria) this;
        }

        public Criteria andListenLessThanOrEqualTo(Boolean value) {
            addCriterion("listen <=", value, "listen");
            return (Criteria) this;
        }

        public Criteria andListenIn(List<Boolean> values) {
            addCriterion("listen in", values, "listen");
            return (Criteria) this;
        }

        public Criteria andListenNotIn(List<Boolean> values) {
            addCriterion("listen not in", values, "listen");
            return (Criteria) this;
        }

        public Criteria andListenBetween(Boolean value1, Boolean value2) {
            addCriterion("listen between", value1, value2, "listen");
            return (Criteria) this;
        }

        public Criteria andListenNotBetween(Boolean value1, Boolean value2) {
            addCriterion("listen not between", value1, value2, "listen");
            return (Criteria) this;
        }

        public Criteria andIsRemoteIsNull() {
            addCriterion("is_remote is null");
            return (Criteria) this;
        }

        public Criteria andIsRemoteIsNotNull() {
            addCriterion("is_remote is not null");
            return (Criteria) this;
        }

        public Criteria andIsRemoteEqualTo(Boolean value) {
            addCriterion("is_remote =", value, "isRemote");
            return (Criteria) this;
        }

        public Criteria andIsRemoteNotEqualTo(Boolean value) {
            addCriterion("is_remote <>", value, "isRemote");
            return (Criteria) this;
        }

        public Criteria andIsRemoteGreaterThan(Boolean value) {
            addCriterion("is_remote >", value, "isRemote");
            return (Criteria) this;
        }

        public Criteria andIsRemoteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_remote >=", value, "isRemote");
            return (Criteria) this;
        }

        public Criteria andIsRemoteLessThan(Boolean value) {
            addCriterion("is_remote <", value, "isRemote");
            return (Criteria) this;
        }

        public Criteria andIsRemoteLessThanOrEqualTo(Boolean value) {
            addCriterion("is_remote <=", value, "isRemote");
            return (Criteria) this;
        }

        public Criteria andIsRemoteIn(List<Boolean> values) {
            addCriterion("is_remote in", values, "isRemote");
            return (Criteria) this;
        }

        public Criteria andIsRemoteNotIn(List<Boolean> values) {
            addCriterion("is_remote not in", values, "isRemote");
            return (Criteria) this;
        }

        public Criteria andIsRemoteBetween(Boolean value1, Boolean value2) {
            addCriterion("is_remote between", value1, value2, "isRemote");
            return (Criteria) this;
        }

        public Criteria andIsRemoteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_remote not between", value1, value2, "isRemote");
            return (Criteria) this;
        }

        public Criteria andProtocolIsNull() {
            addCriterion("protocol is null");
            return (Criteria) this;
        }

        public Criteria andProtocolIsNotNull() {
            addCriterion("protocol is not null");
            return (Criteria) this;
        }

        public Criteria andProtocolEqualTo(Byte value) {
            addCriterion("protocol =", value, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolNotEqualTo(Byte value) {
            addCriterion("protocol <>", value, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolGreaterThan(Byte value) {
            addCriterion("protocol >", value, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolGreaterThanOrEqualTo(Byte value) {
            addCriterion("protocol >=", value, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolLessThan(Byte value) {
            addCriterion("protocol <", value, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolLessThanOrEqualTo(Byte value) {
            addCriterion("protocol <=", value, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolIn(List<Byte> values) {
            addCriterion("protocol in", values, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolNotIn(List<Byte> values) {
            addCriterion("protocol not in", values, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolBetween(Byte value1, Byte value2) {
            addCriterion("protocol between", value1, value2, "protocol");
            return (Criteria) this;
        }

        public Criteria andProtocolNotBetween(Byte value1, Byte value2) {
            addCriterion("protocol not between", value1, value2, "protocol");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIsNull() {
            addCriterion("authentication is null");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIsNotNull() {
            addCriterion("authentication is not null");
            return (Criteria) this;
        }

        public Criteria andAuthenticationEqualTo(String value) {
            addCriterion("authentication =", value, "authentication");
            return (Criteria) this;
        }

        public Criteria andAuthenticationNotEqualTo(String value) {
            addCriterion("authentication <>", value, "authentication");
            return (Criteria) this;
        }

        public Criteria andAuthenticationGreaterThan(String value) {
            addCriterion("authentication >", value, "authentication");
            return (Criteria) this;
        }

        public Criteria andAuthenticationGreaterThanOrEqualTo(String value) {
            addCriterion("authentication >=", value, "authentication");
            return (Criteria) this;
        }

        public Criteria andAuthenticationLessThan(String value) {
            addCriterion("authentication <", value, "authentication");
            return (Criteria) this;
        }

        public Criteria andAuthenticationLessThanOrEqualTo(String value) {
            addCriterion("authentication <=", value, "authentication");
            return (Criteria) this;
        }

        public Criteria andAuthenticationLike(String value) {
            addCriterion("authentication like", value, "authentication");
            return (Criteria) this;
        }

        public Criteria andAuthenticationNotLike(String value) {
            addCriterion("authentication not like", value, "authentication");
            return (Criteria) this;
        }

        public Criteria andAuthenticationIn(List<String> values) {
            addCriterion("authentication in", values, "authentication");
            return (Criteria) this;
        }

        public Criteria andAuthenticationNotIn(List<String> values) {
            addCriterion("authentication not in", values, "authentication");
            return (Criteria) this;
        }

        public Criteria andAuthenticationBetween(String value1, String value2) {
            addCriterion("authentication between", value1, value2, "authentication");
            return (Criteria) this;
        }

        public Criteria andAuthenticationNotBetween(String value1, String value2) {
            addCriterion("authentication not between", value1, value2, "authentication");
            return (Criteria) this;
        }

        public Criteria andIndexTimeIsNull() {
            addCriterion("index_time is null");
            return (Criteria) this;
        }

        public Criteria andIndexTimeIsNotNull() {
            addCriterion("index_time is not null");
            return (Criteria) this;
        }

        public Criteria andIndexTimeEqualTo(Date value) {
            addCriterion("index_time =", value, "indexTime");
            return (Criteria) this;
        }

        public Criteria andIndexTimeNotEqualTo(Date value) {
            addCriterion("index_time <>", value, "indexTime");
            return (Criteria) this;
        }

        public Criteria andIndexTimeGreaterThan(Date value) {
            addCriterion("index_time >", value, "indexTime");
            return (Criteria) this;
        }

        public Criteria andIndexTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("index_time >=", value, "indexTime");
            return (Criteria) this;
        }

        public Criteria andIndexTimeLessThan(Date value) {
            addCriterion("index_time <", value, "indexTime");
            return (Criteria) this;
        }

        public Criteria andIndexTimeLessThanOrEqualTo(Date value) {
            addCriterion("index_time <=", value, "indexTime");
            return (Criteria) this;
        }

        public Criteria andIndexTimeIn(List<Date> values) {
            addCriterion("index_time in", values, "indexTime");
            return (Criteria) this;
        }

        public Criteria andIndexTimeNotIn(List<Date> values) {
            addCriterion("index_time not in", values, "indexTime");
            return (Criteria) this;
        }

        public Criteria andIndexTimeBetween(Date value1, Date value2) {
            addCriterion("index_time between", value1, value2, "indexTime");
            return (Criteria) this;
        }

        public Criteria andIndexTimeNotBetween(Date value1, Date value2) {
            addCriterion("index_time not between", value1, value2, "indexTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}