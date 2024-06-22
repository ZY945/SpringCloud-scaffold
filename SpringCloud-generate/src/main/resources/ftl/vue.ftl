<template>
    <div>
        <p>
            <button @click="add" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-edit red2"></i>新增
            </button> &nbsp;
            <button @click="getAll(1)" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh red2"></i>刷新
            </button>
        </p>
        <table id="simple-table" class="table  table-bordered table-hover">

            <thead>
            <tr>
                <#list fieldList as field>
                    <th>${field.nameCn}</th>
                </#list>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="${domain} in ${domain}s">
                <#list fieldList as field>
                    <td>{{${domain}.${field.nameHump}}}</td>
                </#list>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button @click="edit(${domain})" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        <button @click="del(${domain}.id)" class="btn btn-xs btn-danger">
                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                        </button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">表单</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <#list fieldList as field>
                                <div class="form-group">
                                    <label for="${field.nameHump}"
                                           class="col-sm-2 control-label">${field.nameCn}</label>
                                    <div class="col-sm-10">
                                        <input v-model="${domain}.${field.nameHump}" id="${field.nameHump}"
                                               class="form-control">
                                    </div>
                                </div>
                            </#list>
                        </form>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button @click="save" type="button" class="btn btn-primary">保存</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--
            :list="getAll"

            list: 是子组件暴露出来的一个回调方法;
            getAll: 是父组件的 getAll 方法;
          -->
        <pagination ref="pagination" :list="getAll" :itemCount="8"/>
    </div>
</template>

<script>
    import Pagination from '../../components/pagination'

    export default {
        name: "${domain}",
        components: {
            Pagination,
        },
        data() {
            return {
                ${domain}s: [],
                ${domain}: {
                    <#list fieldList as field>
                    ${field.nameHump}: '',
                    </#list>
                },
            }
        },
        created() {
        },
        mounted() {
            let _this = this;
            _this.$refs.pagination.size = 10;
            _this.getAll(1);
        },
        methods: {
            getAll(page) {
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/list', {
                    page: page,
                    size: _this.$refs.pagination.size,
                }).then(response => {
                    Loading.hide();
                    let resp = response.data;
                    _this.${domain}s = resp.content.list;
                    // 渲染子组件
                    _this.$refs.pagination.render(page, resp.content.total);
                })
            },
            add() {
                let _this = this;
                _this.${domain} = {};
                $("#myModal").modal("show");
            },
            save() {
                let _this = this;

                // 保存校验
                if (1 !== 1
                    <#list fieldList as field>
                    <#if !field.nullAble>
                    || !Validator.require(_this.${domain}.${field.nameHump}, "${field.nameCn}")
                    </#if>
                    <#if (field.length > 0)>
                    || !Validator.length(_this.${domain}.${field.nameHump}, "${field.nameCn}", 1, ${field.length})
                    </#if>
                    </#list>
                ) {
                    return;
                }

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/save',
                    _this.${domain}).then(response => {
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        $("#myModal").modal("hide");
                        _this.getAll(1);
                        Toast.success("保存成功");
                    }
                })
            },
            edit(${domain}) {
                let _this = this;
                // 双向绑定问题: 输入的时候表格也会更新数据: 使用 JQuery 的函数解决问题
                _this.${domain} = $.extend({}, ${domain});
                $("#myModal").modal("show");
            },
            del(id) {
                let _this = this;
                Confirm.show("该操作不可逆转", function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/delete/' + id).then(response => {
                        Loading.hide();
                        let resp = response.data;
                        if (resp.success) {
                            _this.getAll(1);
                            Toast.success("删除成功");
                        }
                    });
                });
            }
        }
    }
</script>

<style scoped>

</style>