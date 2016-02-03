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
    new premiers_pas,
    new pas_suivant
  )
}

class premiers_pas extends HandsOn {
  override def nestedSuites = List(
    new e0_vars_vals,
    new e1_classes,
    new e2_case_classes,
    new e3_boucle_for
  )
}

class pas_suivant extends HandsOn {
  override def nestedSuites = List(
    new e4_listes,
    new e5_maps,
    new e6_sets,
    new e7_option,
    new e8_fonctions_de_plus_haut_niveau,
    new e9_extracteurs_et_patterns
  )
}
