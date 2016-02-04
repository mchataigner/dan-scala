//import com.google.common.reflect.ClassPath
import part1_1._
import part1_2._

import org.scalatest._
import support.{HandsOnSuite, CustomStopper}

class HandsOn extends Suite {
  override def run(testName: Option[String], reporter: Reporter, stopper: Stopper, filter: Filter,
                   configMap: Map[String, Any], distributor: Option[Distributor], tracker: Tracker) {
    if(!CustomStopper.oneTestFailed)
    super.run(testName, reporter, CustomStopper, filter, configMap, distributor, tracker)
  }
}

class HandsOnScala extends HandsOn {
  override def nestedSuites = List(
    new part1_1,
    new part1_2
  )
}

class part1_1 extends HandsOn {
  override def nestedSuites = List(
    new e0_vars_vals,
    new e1_classes,
    new e2_case_classes,
    new e3_for_loops
  )
}

class part1_2 extends HandsOn {
  override def nestedSuites = List(
    new e4_lists,
    new e5_maps,
    new e6_sets,
    new e7_option,
    new e8_higher_order_functions,
    new e9_extractors_and_pattern_matching
  )
}
