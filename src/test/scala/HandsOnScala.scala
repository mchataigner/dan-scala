import part_1_basic._
import part_2_collections._
import part_3_functional_programming._
import part_4_advanced_collections._
import org.scalatest._
import support.CustomStopper
import recorder.ReportToTheStopper

class HandsOn extends Spec {
  override def run(testName: Option[String], args: Args) = {
    if(!CustomStopper.oneTestFailed)
      super.run(testName, args.copy(reporter = new ReportToTheStopper(args.reporter), stopper = CustomStopper))
    else
      SucceededStatus
  }
}

class HandsOnScala extends HandsOn {
  override def nestedSuites = Vector(
    new part_1_basic,
    new part_2_collections,
    new part_3_functional_programming,
    new part_6_sparkintro,
    new part_7_scaldingintro
  )
}

class part_1_basic extends HandsOn {
  override def nestedSuites = Vector(
    new e0_vars_vals,
    new e1_classes,
    new e2_case_classes,
    new e3_for_loops
  )
}

class part_2_collections extends HandsOn {
  override def nestedSuites = Vector(
    new e4_lists,
    new e5_maps,
    new e6_sets,
    new e7_option
  )
}

class part_3_functional_programming extends HandsOn {
  override def nestedSuites = Vector(
    new e8_higher_order_functions,
    new e9_extractors_and_pattern_matching,
    new e10_fp
  )
}

class part_4_advanced_collections extends HandsOn {
  override def nestedSuites = Vector(
    new e11_list,
    new e12_stream,
    new e13_future
  )
}

class part_6_sparkintro extends HandsOn {
  override def nestedSuites = Vector(
    new part_6_sparkintro.Intro
  )
}

class part_7_scaldingintro extends HandsOn {
  override def nestedSuites = Vector(
    new part_7_scaldingintro.Intro
  )
}
