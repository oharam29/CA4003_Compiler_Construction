/* Generated By:JJTree: Do not edit this line. FunctionCall.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class FunctionCall extends SimpleNode {
  public FunctionCall(int id) {
    super(id);
  }

  public FunctionCall(CCALTokeniser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CCALTokeniserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=9967116d0380e470a76cc7d00ec4b030 (do not edit this line) */
