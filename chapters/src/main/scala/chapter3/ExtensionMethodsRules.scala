package chapter3

object ExtensionMethodsRules:

  /** [Rule 1]
   *
   * The extension method is visible under a simple name,
   * by being defined or inherited or imported in a scope enclosing the reference.
   */
  object Rule1:
    case class Entity(id: Long, scoreA: Int, scoreB: Int, scoreC: Int)
    trait EntityOps:
      extension (entity: Entity)
        def sum: Int =
          entity.scoreA + entity.scoreB + entity.scoreC

        def avg: Double =
          // extension method `sum` defined in same scope
          entity.sum / 3

    object EntityOpsExt extends EntityOps:
      extension (entity: Entity)
        def avgAsInt: Int =
          // extension method `avg` brought into scope via inheritance from EntityOps
          entity.avg.toInt

    trait ListEntityOps:
      import EntityOpsExt.{ avg => avgScore } // brings `avg` into scope as `avgScore`
      extension (entities: List[Entity])
        def avg: Double =
          entities.nonEmpty match
            // extension methods `avg` imported and thus in scope
            case true  => entities.map(_.avgScore).sum / entities.length
            case false => 0.0

  /** [Rule 2]
   *
   * The extension method is a member of some given instance that is visible at the point of the reference.
   */
  object Rule2:
    given ops1: Rule1.EntityOps()  // brings `avg` into scope
    val entity = Rule1.Entity(1L, 100, 80, 70)
    entity.avg

  /** [Rule 3]
   *
   * The reference is of the form r.m and the extension method is defined in the implicit scope of the type of r.
   */
  object Rule3:
    case class Entity(id: Long, scoreA: Int, scoreB: Int, scoreC: Int)
    object Entity:
      extension (entity: Entity)
        def avg: Double =
          (entity.scoreA + entity.scoreB + entity.scoreC) / 3

      extension (entities: List[Entity])
        def avg: Double =
          entities.nonEmpty match
            case true  => entities.map(_.avg).sum / entities.length
            case false => 0.0

    val list = List(Entity(1L, 100, 80, 50), Entity(2L, 50, 60, 70), Entity(3L, 100, 100, 100))
    list.avg


  /** [Rule 4]
   *
   * The reference is of the form r.m and the extension method is defined in the implicit scope of the type of r.
   */
  object Rule4:
    trait EntityOps:
      extension (entity: Entity)
        def avg: Double =
          (entity.scoreA + entity.scoreB + entity.scoreC) / 3

      extension (entities: List[Entity])
        def avg: Double =
          entities.nonEmpty match
            case true  => entities.map(_.avg).sum / entities.length
            case false => 0.0

    case class Entity(id: Long, scoreA: Int, scoreB: Int, scoreC: Int)
    object Entity:
      given ops: EntityOps()

    val list = List(Entity(1L, 100, 80, 50), Entity(2L, 50, 60, 70), Entity(3L, 100, 100, 100))
    list.avg
