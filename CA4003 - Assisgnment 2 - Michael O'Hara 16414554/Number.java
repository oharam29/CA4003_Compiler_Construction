/* Generated By:JJTree: Do not edit this line. Number.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class Number extends SimpleNode {
  public Number(int id) {
    super(id);
  }

  public Number(CCALTokeniser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(CCALTokeniserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4cb7beb5e1d1f4d2f33b7b2514cf9fdd (do not edit this line) */